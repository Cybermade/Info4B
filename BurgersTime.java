

import java.util.*;


public class BurgersTime {
        private int[] Decore;
        private List<String> Constituants;
        private List<String> Entites;
        private int NB_COLONNES ;
        private int NB_LIGNES ;
        private int Sommet = 0;
        private String Echelle;// int 2
        private String Sol;//int 1
        private String Vide;// int 0
        private int[] Pile;
        public BurgersTime(int NB_LIGNES,int NB_COLONNES){
                this.NB_LIGNES = NB_LIGNES;
                this.NB_COLONNES = NB_COLONNES;
                
                this.Decore = new int[NB_LIGNES * NB_COLONNES];
                this.Pile = new int [NB_LIGNES * NB_COLONNES];
                this.Sommet = 0;
                this.Echelle = "#";
                this.Sol="—";
                this.Vide=" ";
                
        }
        private int getID(int ligne, int colonne)
        {
                return ligne * NB_COLONNES + colonne;
        }
        private int getLigne (int id )
        {
                return id / NB_COLONNES;
        }
        private int getCol(int id)
        {
                return id % NB_COLONNES ;
        }
        private void modifie(int ligne, int colonne, int x)
        {
                Decore[getID(ligne, colonne)] = x;
        }
        private int lit(int ligne, int colonne)
        {
                return Decore[getID(ligne, colonne)];
        }
        private int getNbLignes()
        {
                return NB_LIGNES;
        }
        private int getNbColonnes()
        {
                return NB_COLONNES;
        }

        private void push(int x)
        {       
                Pile[Sommet]= x;
                Sommet++;
        }
        private int pop()
        {       Sommet--;
                return Pile[Sommet];
        }
        public void test()
        {
            
        }
        public boolean connexe()
        {
                int nbcasesb = 0, id = 0, nbcasesm = 0;
                int ligne, colonne;
            
                for (int m = 0; m < NB_LIGNES; m++)
                {
                    for (int l = 0; l < NB_COLONNES; l++)
                    {
                        if ((lit(m, l))==0)
                        {
                            nbcasesb++;
                            id = getID(m, l);
                        }
                    }
                }
                modifie( getLigne(id), getCol(id), 2) ;
                if (nbcasesb==0)
                {
                    System.out.println("Erreur Pas de cases blanches");
                    System.exit(0);
                }
                push(id);
                while (Sommet>0)
                {
                    id = pop();
                    ligne = getLigne(id);
                    colonne = getCol(id);
            
                    for (int m = -1; m < 2; m = m + 2) // on compare verticalement
                    {
                        if (ligne + m >= 0 && ligne + m < NB_LIGNES && (lit(ligne+m,colonne))==0)//lit(ligne+m,colonne)
                        {
                            modifie(ligne+m,colonne,2);
                            push(getID(ligne + m, colonne));
                        }
                    }
            
                    for (int l = -1; l < 2; l = l + 2) //on compare horizontalement
                    {
                        if (colonne + l >= 0 && colonne + l < NB_COLONNES && (lit(ligne, colonne + l))==0)
                        {
                            modifie(ligne , colonne + l , 2);
                            push(getID(ligne, colonne + l));
                        }
                    }
                }
                for (int m = 0; m < NB_LIGNES; m++)
                {
                    for (int l = 0; l <  NB_COLONNES; l++)
                    {
                        if (lit(m,l) == 2)
                        {   
                            modifie(m,l,0);
                            nbcasesm++;
                        }
                    }
                }
                if (nbcasesm == nbcasesb)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            
        }
        public void genDecore(int nb)
        {
                int id=0, IDlast, IDAvantModif, limite = 0;
                int K = 0;//nbr cases vides 
                IDlast = getID(NB_LIGNES - 1, NB_COLONNES - 1); // derniére case
            
                if (nb > (NB_LIGNES * NB_COLONNES) || nb <= 0)
                {
                    if(nb>(NB_LIGNES * NB_COLONNES))System.out.println("\nPas possible d'avoir plus de cases blanches que le nombre de cases totale\n");
                    else if(nb<=0)System.out.println("\nPas possible d'avoir un nombre négatif ou égal à 0\n");
                    System.exit(0);
                }
                nb = nb - 2;     //j'enléve 2 du nombre voulu(première et derniére)
                K = NB_COLONNES * NB_LIGNES - 2; //j'enléve 2 du nombre totale de cases blanches(première et derniére)
            
                while (nb != K && (limite <= (NB_COLONNES * NB_LIGNES)))
                {
                    
            
                    while (id == 0 || id == IDlast)
                        id = (int)Math.random() % (NB_COLONNES * NB_LIGNES - 1); //je refais tant que j'ai une id de la premiere ou de la derniere case
            
                    IDAvantModif = lit(getLigne(id), getCol(id)); //je prends la valeure de la case si elle était blanche(0) ou noire(1)
            
                    modifie(getLigne(id), getCol(id), 1);
            
                    if (connexe() && IDAvantModif==0) //je test en ayant mit le case en noir si c'est toujours connexe et la case d'avant était bien blanche
                    {
                        K--; //car si je mets noir sur noir ça ne sert a rien
                        limite = 0;
                    }
                    else if (IDAvantModif == 0)
                    {
                        modifie(getLigne(id), getCol(id), 0);
            
                    } //si elle était blanche et je l'ai noircie mais elle était critique je reviens en arriére(Backtrack)
            
                    else
                        limite++; //si je rentre dans la boucle et je fais rien je limite++
            
                    id=0;       
                        
                }

        }
        private void mettreEchelle()
        {
                for(int i=1;i<NB_LIGNES;i=i+2)
                {
                        for(int j=0;j<NB_COLONNES;j++)
                        {
                                if(Decore[getID(i, j)] == 0)
                                Decore[getID(i,j)]=2;
                        }
                }
        }
        
        public void Affiche()
        {
                for(int i=0;i< NB_LIGNES; i++)
                {
                        for(int j=0;j<NB_COLONNES;j++)
                        {       if(Decore[getID(i,j)]==0)
                                System.out.print(Sol);
                                else if(Decore[getID(i,j)]==1)
                                System.out.print(Vide);
                                else
                                System.out.print(Echelle);

                        }
                        System.out.println("");
                }
        }
}

