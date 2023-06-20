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

/**
 *
 * @author Jelena
 */
@Named(value = "omb")
@RequestScoped
public class OrganizatorManagdedBean {
    
    private List<Knjiga> knjige = new ArrayList<>();

    
    public OrganizatorManagdedBean() {
    }
    
    @PostConstruct
    public void initData(){
        Connection conn = DB.getInstance().getConnection();
        try(
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT* FROM knjige WHERE status=2");
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
    
    public String odobri(Knjiga k){
        Connection conn = DB.getInstance().getConnection();
        try(
                PreparedStatement stmt = conn.prepareStatement("UPDATE knjige SET status=3 WHERE naziv=?")
                ){
            stmt.setString(1, k.getNaziv());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(OrganizatorManagdedBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            DB.getInstance().putConnection(conn);
        }
        return "organizator.xhtml?faces-redirect=true";
    }
    
    public String odbij(Knjiga k){
        Connection conn = DB.getInstance().getConnection();
        try(
                PreparedStatement stmt = conn.prepareStatement("UPDATE knjige SET status=4 WHERE naziv=?")
                ){
            stmt.setString(1, k.getNaziv());
            stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(OrganizatorManagdedBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            DB.getInstance().putConnection(conn);
        }
        return "organizator.xhtml?faces-redirect=true";
    }

    public List<Knjiga> getKnjige() {
        return knjige;
    }

    public void setKnjige(List<Knjiga> knjige) {
        this.knjige = knjige;
    }
    
}
