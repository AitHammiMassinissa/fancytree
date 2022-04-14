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

public class AddEntryToLDAP 
{
	
	/**
	 * Methode initialisant une connexion au serveur ldap et permettant d'ajouter un nouvel utilisateur
	 * 
	 */
	private static void addUser(String user, String mail, String userpass, String groupe, String uidN, String gidN)
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
			objClasses.add("inetOrgPerson");
			final Attribute objClasses1 = new BasicAttribute("objectClass");
			objClasses.add("posixAccount");


			// assignation du nom d'utilisateur, prenom et nom
			final Attribute uid = new BasicAttribute("uid", user);
			final Attribute surName = new BasicAttribute("sn", user);
			final Attribute givenName = new BasicAttribute("givenName", user);
			final Attribute commonName = new BasicAttribute("cn", user);
			final Attribute email = new BasicAttribute("mail", mail);
			final Attribute displayName = new BasicAttribute("displayName", user);
			
			// ajout du nom de passe
			final Attribute userPassword = new BasicAttribute("userpassword", userpass);
			final Attribute uIdNumber= new BasicAttribute("uidNumber", uidN);
			final Attribute gIdNumber= new BasicAttribute("gidNumber", gidN);
			final Attribute homeDir  = new BasicAttribute("homeDirectory", "/home/");

			// ajout du tout au conteneur
			container.put(objClasses);
			container.put(uid);
			container.put(surName);
			container.put(givenName);
			container.put(commonName);
			container.put(email);
			container.put(displayName);
			container.put(userPassword);
			container.put(uIdNumber);
			container.put(gIdNumber);
			container.put(homeDir);

			// creation de l'entrée
			dctx.createSubcontext(getUserDN(user, groupe), container);
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
	private static String getUserDN(final String userName, final String groupe) 
	{
		
		final String g= ",cn="+groupe+",dc=vps,dc=ovh,dc=net";
		String userDN = new StringBuffer().append("uid=").append(userName).append(g).toString();
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
		//isValid(args[1]);
		addUser(args[0], args[1], args[2], args[3], args[4], args[5]);
	}

}
