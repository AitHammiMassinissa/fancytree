import java.util.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEntryGroupToLDAP 
{
	
	/**
	 * Methode initialisant une connexion au serveur ldap et permettant d'ajouter un nouvel utilisateur
	 * 
	 */
	private static void addUser(String group, String gid)
	{	
		
		final Hashtable<Object, Object> env = new Hashtable<Object, Object>();
		DirContext dctx = null;
		
		try {
			String url      = "ldap://141.95.0.7:389";
			String conntype = "simple";
			String AdminDn  = "cn=admin,dc=vps,dc=ovh,dc=net";
			String password = "CS!DC@UniTwin";

			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, url);
			env.put(Context.SECURITY_AUTHENTICATION, conntype);
			env.put(Context.SECURITY_PRINCIPAL, AdminDn);
			env.put(Context.SECURITY_CREDENTIALS, password);
			dctx = new InitialDirContext(env);
			// creation d'un containeur pour l'ensemble des attributs
			final Attributes container = new BasicAttributes();

			// creation du objectclass à ajouter
			
			final Attribute objClasses = new BasicAttribute("objectClass");
			objClasses.add("top");
			
			final Attribute objClasses1 = new BasicAttribute("objectClass");
			objClasses1.add("posixGroup");

			// assignation du nom du groupe, prenom et nom
			final Attribute cn = new BasicAttribute("cn", group);
			final Attribute gidN = new BasicAttribute("gidNumber", gid);
			

			// ajout du tout au conteneur
			container.put(objClasses);
			container.put(objClasses1);
			container.put(cn);
			container.put(gidN);
			
			// creation de l'entrée
			dctx.createSubcontext(getGroupDN(group), container);
		}
		catch (final NamingException e) 
		{
					System.out.println("Error in closing ldap " + e);
		} 
		finally {
			if (null != dctx) {
				try {
					dctx.close();
				} catch (final NamingException e) {
					System.out.println("Error in closing ldap " + e);
				}
			}
		}
	}

	/**
	 * Methode permettant d'avoir un bref recapitulatif du nouvel utilisateur ajouté
	 * 
	 */
	private static String getGroupDN(final String groupe) 
	{
		String userDN = new StringBuffer().append("cn=").append(groupe).append(",dc=vps,dc=ovh,dc=net").toString();
		System.out.println(userDN);
		return userDN;
	}
	
	/**
	 * Methode permettant de verifier si l'adresse mail est valide
	 * 
	 */
	public static boolean isValid(String email)
    {
		//Initialisation d'un element specifiant la structure d'une adresse mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        
        //Comparaison avec l'adresse donnée en paramètre dans le but de verifier si elle est valide
        
        //Si adresse non-valide
        if (!pat.matcher(email).matches()) 
        {
			//Creation d'un message d'erreur indiquant que l'adresse n'est pas valide
			try
			{
				throw new Exception("Email non-valid");
			 }
			 
			 //Affichage de l'erreur et sortie du programme
			 catch(Exception e)
			 {
				 e.printStackTrace();
				 System.exit(0);
			 }
         }
         
         //Si l'adresse est valide alors, on retourne true
        return true;
    }
    
    
    
    /*-----------------------------------------------------------*/
	/*------------------Programme principal----------------------*/
	/*-----------------------------------------------------------*/
	public static void main(String[] args)
	{
		addUser(args[0], args[1]);
	}

}

