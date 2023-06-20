/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedBeans;

import entities.Knjiga;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;
import util.SessionUtils;

/**
 *
 * @author Jelena
 */
@Named(value = "lmb")
@RequestScoped
public class LoginManagedBean  {
    
    private String username;
    private String password;
    private String group;
    private String error;
    private List<Knjiga> knjige = new ArrayList<>();
    
    public LoginManagedBean() {
    }
    
    @PostConstruct
    public void initData(){
        Connection conn = DB.getInstance().getConnection();
        try(
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT* FROM knjige");
                PreparedStatement stmt2 = conn.prepareStatement("SELECT ime, prezime FROM korisnici WHERE korisnicko_ime=?")
                ){
            while(rs.next()){
                Knjiga k = new Knjiga();
                
                k.setNaziv(rs.getString("naziv"));
                k.setStatus(rs.getInt("status"));
                
                String p = rs.getString("pisac");
               
                stmt2.setString(1, p);
                
                try(ResultSet rs2=stmt2.executeQuery()){
                    if(rs2.next()){
                    k.setIme_pisca(rs2.getString("ime"));
                    k.setPrezime_pisca(rs2.getString("prezime"));
                    }
                }
                knjige.add(k);
            }
        }catch (SQLException ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            DB.getInstance().putConnection(conn);
        }
    }

    public String login(){
        if(username.isEmpty() || password.isEmpty() || group.isEmpty()){
            error = "Niste uneli sve podatke";
            return null;
        }
        Connection conn = DB.getInstance().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("SELECT ime, prezime FROM korisnici WHERE korisnicko_ime=? AND lozinka=? AND tip=?")){
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, group);
            
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    SessionUtils.setAttribute("korisnicko_ime", username);
                    return group + ".xhtml?faces-redirect=true";
                }else{
                    error = "Pogresno korisnicko ime, lozinka ili tip.";
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            DB.getInstance().putConnection(conn);
        }
        return null;
    }
    
    public String logout(){
        SessionUtils.getSession().removeAttribute("korisnicko_ime");
        return "index.xhtml?faces-redirect=true";
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public List<Knjiga> getKnjige() {
        return knjige;
    }

    public void setKnjige(List<Knjiga> knjige) {
        this.knjige = knjige;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    
}