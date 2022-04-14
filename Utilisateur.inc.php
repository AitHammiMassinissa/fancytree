<?php

/*classe permettant de representer les tuples de la table client */
class Utilisateur
{
      /*avec PDO, il faut que les client_ip_Addresss attributs soient les mêmes que ceux de la table*/
      private $client_id;
      private $client_ip_address;
      private $client_full_name;
	  private $client_email;
	  private $client_phone_number;
	  private $client_password;
	  private $client_log;
	  //private $date          ;
	  //private $Utilisateurs=array();

      /* Les méthodes qui commencent par __ sont des methodes magiques */
      /* Elles sont appelées automatiquement par php suite à certains événements. */
      /* Ici c'est l'appel à new sur la classe qui déclenche l'exécution de la méthode */
      /* des valeurs par défaut doivent être spécifiées pour les paramètres du constructeur sinon
      	 il y aura une erreur lorsqu'il sera appelé automatiquement par PDO 
       */    
      
      public function __construct($i="", $n="", $p="", $r="", $g="",$m="", $dC="")
	  {
      	$this->client_id    = $i               ;
	  	$this->client_ip_Address            = $n               ;
	  	$this->client_full_name         = $p               ;
		$this->client_email           = $r               ;
		$this->client_phone_number         = $g               ;
		$this->client_password = $m               ;
		$this->client_log  = $dC;
		/*if($this->client_log==null){$this->client_log = $this->laDate(0) ;}
		$this->date           = $this->laDate(0) ; // Appeler une variable envoyée par 
											// le bouton "valider" de la méthode "modifier" à la place du 0*/
      }

      public function getId()      { return $this->client_id      ; }
      public function getClient_ip_Address()     { return $this->client_ip_address              ; }
      public function getclient_full_name()  { return $this->client_full_name           ; }
	  public function getclient_email()    { return $this->client_email             ; }
	  public function getclient_phone_number()  { return $this->client_phone_number           ; }
      public function getclient_password()     { return $this->client_password              ; }
	  public function getDateDeC() { return $this->client_log   ; }
      public function getDate()    { return $this->date             ; }
	
	  function laDate($indice)
	  {
		//string date(string $format [, entier $timestamp = time()])
		$dateDecale = strtotime($indice." days ", time());

		$tabJours   = array( "Dim", "Lun"  , "Mar"  , "Mer", "Jeu", "Ven","Sam")                                                            ;
		$tabMois    = array("Janvier", "Fevrier", "Mars"    , "Avril", "Mai"     , "Juin"  , "Juillet" , "Aout" , "Septembre" , "Octobre" , "Novembre" , "Decembre") ;

		$numJr   = intval(date('d',$dateDecale)) ;
		$jour    = intval(date('w',$dateDecale)) ;
		$mois    = intval(date('m',$dateDecale)) ;
		$annee   = intval(date('Y',$dateDecale)) ;
		$numJrAn = intval(date('z',$dateDecale)) ;

		$date    = $numJr.'/'.$mois.'/'.$annee;
		return $date ;
	  }
}
?>

