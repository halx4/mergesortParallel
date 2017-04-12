package com.foivos.mergesortParallel;

public class Mergesort extends Thread
{

	private int[] pinakas;
	private int start,end;
	private ParentInterface par;
	
	
   public Mergesort(int[] pinakas,int start,int end,ParentInterface par)
   {
	   this.pinakas=pinakas;
	   this.par=par;
	   this.start=start;
	   this.end=end;
	   
   		}
   
 //-------------------------------------
  
   public void run(){
	  
	 
	   mergesort(pinakas, start, end);

	  par.childFinished();
	  
   }
   
//-----------------------------------------------

   
   
   private static void mergesort(int[ ] pinakas, int first, int n)
   {
	   
	  
      int n1; // μέγεθος του πρώτου μισού του πίνακα
      int n2; //μέγεθος του δευτερου μισού του πίνακα

      if (n > 1)
      {
         
         n1 = n / 2;
         n2 = n - n1;

         mergesort(pinakas, first, n1);      // ταξινόμησε το πρώτο μισο
         mergesort(pinakas, first + n1, n2); // ταξινόμησε το δεύτερο μισό

         // Κάνε τη συνένωση των δύο υποπινάκων
         merge(pinakas, first, n1, n2);
         
         
      }
   } 
  
   public static void merge(int[ ] pinakas, int first, int n1, int n2)

   {
      int[ ] temp = new int[n1+n2]; 
      int copied  = 0; 
      int copied1 = 0; 
      int copied2 = 0; 
      int i;           

      // συνενωση μεχρι ένας απο τους δύο υποπίνακες να αδειάσει
      while ((copied1 < n1) && (copied2 < n2))
      {
         if (pinakas[first + copied1] < pinakas[first + n1 + copied2])
            temp[copied++] = pinakas[first + (copied1++)];
         else
            temp[copied++] = pinakas[first + n1 + (copied2++)];
      }

      // Αφού ο ένας υποπίνακας εχει αδειάσει, αντιγραφω τα υπολοιπα στοιχεια.
      while (copied1 < n1)
         temp[copied++] = pinakas[first + (copied1++)];
      
      while (copied2 < n2)
         temp[copied++] = pinakas[first + n1 + (copied2++)];

      // μεταφορά πίσω στις θέσεις του αρχικού πίνακα
      for (i = 0; i < n1+n2; i++)
         pinakas[first + i] = temp[i];
   }
//-------------------------------------------------

   
   
}


