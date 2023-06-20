/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedBeans;

import entities.Knjiga;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;

import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
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
@Named(value = "pmb")
@SessionScoped
public class PisacManagedBean implements Serializable{
     
    private List<String> knjige;
    private String naziv;
    private String br_strana;
    private String naziv_knjige;
   

    public PisacManagedBean() {
    }
    
    @PostConstruct
    public void initData(){
        
        knjige=new ArrayList<>();
        Connection conn = DB.getInstance().getConnection();
        
        try(PreparedStatement stmt = conn.prepareStatement("SELECT naziv FROM knjige WHERE pisac=?")){
            stmt.setString(1, (String) SessionUtils.getAttribute("korisnicko_ime"));
            
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    String k = rs.getString("naziv");
                    
                    knjige.add(k);
                }
            }
    }   catch (SQLException ex) {
            Logger.getLogger(PisacManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            DB.getInstance().putConnection(conn);
        }
    }
    
    public String promote(){
        Connection conn = DB.getInstance().getConnection();
        try(
                PreparedStatement stmt = conn.prepareStatement("UPDATE knjige SET status=2  WHERE naziv=?")
                ){
            stmt.setString(1, naziv_knjige);
            stmt.execute();
    }   catch (SQLException ex) {
            Logger.getLogger(PisacManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            DB.getInstance().putConnection(conn);
        }
         return "pisac.xhtml?faces-redirect=true";
    }
    
    public String add(){
//        if(naziv.isEmpty() || br_strana.isEmpty()){
//            error = "Niste uneli sve podatke";
//            return null;
//        }
        Connection conn = DB.getInstance().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO knjige( naziv, broj_strana, pisac, status) VALUES(?, ?, ?, 1)")){
            //setInt
            stmt.setString(1, naziv);
            stmt.setInt(2, Integer.parseInt(br_strana));
            stmt.setString(3, (String) SessionUtils.getAttribute("korisnicko_ime"));
            stmt.execute();
                        
        } catch (SQLException ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            DB.getInstance().putConnection(conn);
        }
        return null;
    }
    
    public List<String> getKnjige() {
        return knjige;
    }

    public void setKnjige(List<String> knjige) {
        this.knjige = knjige;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getBr_strana() {
        return br_strana;
    }

    public void setBr_strana(String br_strana) {
        this.br_strana = br_strana;
    }

    public String getNaziv_knjige() {
        return naziv_knjige;
    }

    public void setNaziv_knjige(String naziv_knjige) {
        this.naziv_knjige = naziv_knjige;
    }

}
