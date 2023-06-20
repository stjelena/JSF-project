/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;
 
public class Knjiga {
    
    //private int idK;
    private String naziv;
   // private int brStrana;
    private int status;
    private String ime_pisca;
    private String prezime_pisca;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getIme_pisca() {
        return ime_pisca;
    }

    public void setIme_pisca(String ime_pisca) {
        this.ime_pisca = ime_pisca;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrezime_pisca() {
        return prezime_pisca;
    }

    public void setPrezime_pisca(String prezime_pisca) {
        this.prezime_pisca = prezime_pisca;
    }

    
    
    
    
    
}
