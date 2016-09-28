package dmpro.utils;

import java.util.Random;



public class Die {
  Random r=new Random();
  Dice dieType;
  
  public Die(Dice d) {
	  this.dieType = d;
  }
  public Die() {}
  //for use with spell parsing 2016.08.28???
  public int rollPrint(int times) {
	  int sum=0;
	  int r=0;
	  while (times-->0)  { 
		  r = roll();
		  sum+=r;
		  System.out.printf("Dice Roll of %s:  %d :  Running Total : %d\n", this.dieType, r, sum);
	  }
	  return sum;
  }
  public int roll(int times) {
	  int sum=0;
	  while (times-->0) sum+=roll();
	  return sum;
  }
  public int roll() {
	return r.nextInt(dieType.sides()) + 1 ;
  }

 
  public void setDieType(Dice d) {
		this.dieType = d;
  }

  public static void main(String[] args){
	  Die d = new Die();
	  //System.out.println(args);
	  //System.out.println(Dice.d4.sides());
	  //System.out.println(Dice.d4);
	  
	 //Options o = new Options();
	 
	 
	  
	  int roll;
	  try {
		  switch (Dice.valueOf(args[0])) {
			  case d4:
				  d.dieType =Dice.d4;
				  break;
			  case d6:
				  d.dieType = Dice.d6;
				  break;
			  case d8:
				  d.dieType = Dice.d8;
				  break;
			  case d10:
				  d.dieType = Dice.d10;
				  break;
			  case d12:
				  d.dieType =Dice.d12;
				  break;
			  case d20:
				  d.dieType = Dice.d20;
				  break;
		      default:
		    	  System.out.println("Please choose a valid Die!");
		    	  
		  }
		  
		  if (d.dieType != null) {
			  int rollCount = 1;
			  if (args.length == 2)
				  rollCount = Integer.parseInt(args[1]);
			  roll = d.rollPrint(rollCount);
//			  System.out.format("Die Roll %s = %d\n", d.dieType, roll);
		  }
					  
					  
				
			
			
	  } catch (ArrayIndexOutOfBoundsException e) {
		  
		  

	  }  
  }

  
}
