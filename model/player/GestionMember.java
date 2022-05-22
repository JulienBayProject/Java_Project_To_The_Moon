package model.player;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class GestionMember {

    @Expose
    private List<Member> members;

    /**
     * Constructeur creant un liste vide des membres 
     */
    public GestionMember() {
        members = new ArrayList<Member>();
    }

    /**
     * ajoute un membre a la liste sauf il existe une occurance de celui-ci present dans la liste
     * @param m Member
     */
    public void add(Member m) {
        if(!contains(m)) {
            members.add(m);
        }
    }

    /**
     * retire un membre de la liste
     * @param m Member
     */
    public void remove(Member m) {
        members.remove(m);
    }
    
    /**
     * verifie si un membre est present dans la liste
     * @param m Member
     * @return true si membre present
     */
    public boolean contains(Member m) {
    	return members.contains(m);
    }

    /**
     * Verifie la disponibilite  d'un nom dans la liste 
     * @param name
     * @return true si pas  present
     */
    public boolean availableName(String name) {
    	for (Member m : members) {
			if (m.getNickName().equals(name))
				return false;
		}
    	return true;
    }
    
    /**
     * Remplace un membre dans la liste par un autre membre
     * @param old Member a remplacer
     * @param newM Member remplacant
     */
    public void replaceMember(Member old , Member newM) {
    	members.set(members.indexOf(old), newM);
    }
    
    /**
     * renvois la liste des membres
     * @return
     */
    public List<Member> getMembers(){
        List<Member> newMembers = new ArrayList<Member>();
        return members;
    }

    /**
     * retire tout les occurances des membres presents dans la liste en argument dans la liste des membres
     * @param l List<Memver> a supprimer
     */
    public void removeAll(List<Member> l) {
    	members.removeAll(l);
    }
}