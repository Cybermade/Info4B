
import java.util.*;

class Pop extends Thread
{   private Decore D;
    Pop()
    {
        this.D=D;
    }
    public run()
    {

    }
}
class Decore {
    private int[][] decor;
    private List<String> Entites;
    private List<String> Constituants;
    private int NB_COLONNES;
    private int NB_LIGNES;
    private String assiette;
    private String echelle;
    private int poivre;
    private String sol;
    private String vide;
    private String aliment;
    private Boolean Envie;
    private int[] burger;


    Decore(int[][] decor, List<String> Entites, int NB_LIGNES, int NB_COLONNES) {
        this.decor = decor;
        this.Entites = Entites;
        this.sol = "—";// ——
        this.poivre=3;
        this.vide = " ";
        this.echelle = "#";
        this.assiette = "|___|";
        this.aliment = "~";
        this.Envie = true;
        this.burger = new int[4];

        this.NB_COLONNES = NB_COLONNES;
        this.NB_LIGNES = NB_LIGNES;
    }
    public String indexEntites(int id)
    {
        return Entites.get(id);
    } 

    private boolean enjeu() {
        if (burger[0] == burger[1] && burger[1] == burger[2] && burger[2] == burger[3] && burger[0] == 4)
            return false;
        return true;
    }

    private int getID(int ligne, int colonne) {
        return ligne * NB_COLONNES + colonne;
    }

    private int getLigne(int id) {
        return id / NB_COLONNES;
    }

    private int getCol(int id) {
        return id % NB_COLONNES;
    }
    
    private void deplacerEntites(char dep, String P) {
        int Ligp = getLigne(Entites.indexOf(P));
        int Colp = getCol(Entites.indexOf(P));

        if (dep == 'z' && Ligp - 1 >= 0 && (decor[Ligp - 1][Colp] == 2)) {

            if (Entites.get(getID(Ligp - 2, Colp)) != "0" && Entites.get(getID(Ligp - 2, Colp)) != "~") {
                Entites.set(getID(Ligp, Colp), "0");
                Envie = false;
            } else {
                if (Entites.get(getID(Ligp - 2, Colp)) == "~")
                    deplaceraliment(Ligp - 2, Colp);
                Entites.set(getID(Ligp - 2, Colp), P);
                Entites.set(getID(Ligp, Colp), "0");
            }
        } else if (dep == 's' && Ligp + 1 < NB_LIGNES && (decor[Ligp + 1][Colp] == 2)) {

            if (Entites.get(getID(Ligp + 2, Colp)) != "0" && Entites.get(getID(Ligp + 2, Colp)) != "~") {
                Entites.set(getID(Ligp, Colp), "0");
                Envie = false;
            } else {
                if (Entites.get(getID(Ligp + 2, Colp)) == "~")
                    deplaceraliment(Ligp + 2, Colp);
                Entites.set(getID(Ligp + 2, Colp), P);
                Entites.set(getID(Ligp, Colp), "0");
            }
        } else if (dep == 'd' && Colp + 1 < NB_COLONNES && decor[Ligp][Colp + 1] == 1) {

            if (Entites.get(getID(Ligp, Colp + 1)) != "0" && Entites.get(getID(Ligp, Colp + 1)) != "~") {
                Entites.set(getID(Ligp, Colp), "0");
                Envie = false;
            } else {
                if (Entites.get(getID(Ligp, Colp + 1)) == "~")
                    deplaceraliment(Ligp, Colp + 1);
                Entites.set(getID(Ligp, Colp + 1), P);
                Entites.set(getID(Ligp, Colp), "0");
            }
        } else if (dep == 'q' && Colp - 1 >= 0 && decor[Ligp][Colp - 1] == 1) {

            if (Entites.get(getID(Ligp, Colp - 1)) != "0" && Entites.get(getID(Ligp, Colp - 1)) != "~") {
                Entites.set(getID(Ligp, Colp), "0");
                Envie = false;
            } else {
                if (Entites.get(getID(Ligp, Colp - 1)) == "~")
                    deplaceraliment(Ligp, Colp - 1);
                Entites.set(getID(Ligp, Colp - 1), P);
                Entites.set(getID(Ligp, Colp), "0");
            }
        }

    }

    private void deplaceraliment(int Liga, int Cola) {

        int i = 1;

        while (Liga + i < NB_LIGNES && (decor[Liga + i][Cola] == 0 || decor[Liga + i][Cola] == 2))
            i++;
        if (Liga + i >= NB_LIGNES) {
            burger[Cola / (NB_COLONNES / 4)]++;
            Entites.set(getID(Liga, Cola), "0");
        } else if (Entites.get(getID(Liga + i, Cola)) == Entites.get(getID(Liga, Cola))) {
            deplaceraliment(Liga + i, Cola);
            Entites.set(getID(Liga, Cola), "0");
            Entites.set(getID(Liga + i, Cola), aliment);
        }

        else {
            Entites.set(getID(Liga, Cola), "0");
            Entites.set(getID(Liga + i, Cola), aliment);
        }
    }

    private void afficheEntitess(int i) {
        boolean done = false;
        for (int j = 0; j < NB_COLONNES; j++) {
            if (Entites.get((i * NB_COLONNES) + j) != "0")
                done = true;

        }
        if (done) {
            for (int j = 0; j < NB_COLONNES; j++) {
                if (Entites.get((i * NB_COLONNES) + j) != "0") {
                    System.out.print(Entites.get((i * NB_COLONNES) + j));

                } else
                    System.out.print(vide);

            }
            System.out.println();
        }
    }
    private int distMin(int id1,int id2)//djikstra
    {
        
    int[][] P=new int[NB_LIGNES][NB_COLONNES];
    int[][] utilise=new int[NB_LIGNES][NB_COLONNES];
    int Comp=0,nbcasesb=0,min=NB_LIGNES*NB_COLONNES;
    int idtemp=0;
    int l,c;
    for(int i=0;i<NB_LIGNES;i++)
    {   for(int j=0;j<NB_COLONNES;j++)
        {    if(decor[i][j]!=0)
                {P[i][j]=NB_LIGNES*NB_COLONNES;
                nbcasesb++; 
                
                utilise[i][j]=0;
                        
                }
            else
                {P[i][j]=-1;
                utilise[i][j]=-1; 
                }
        }
    }
    
    P[getLigne(id1)][getCol(id1)]=0;
    
    while(nbcasesb!=Comp)
    {for(int i=0;i<(NB_LIGNES);i++)
    {   for(int j=0;j<NB_COLONNES;j++)
        {    if(P[i][j]!=-1 && P[i][j]<min && utilise[i][j]==0)
                {min=P[i][j];
                idtemp=getID(i, j);
                
                
                
                }
        }
    }
    utilise[getLigne(idtemp)][getCol(idtemp)]=1;
    
    Comp++;
    l=getLigne(idtemp);
    c=getCol(idtemp);
    if ( l-1>=0  && P[l-1][c]>(P[getLigne(idtemp)][getCol(idtemp)]+1) && utilise[l-1][c]==0 )
        P[l-1][c]=P[getLigne(idtemp)][getCol(idtemp)]+1;
    if ( l+1<NB_LIGNES  && P[l+1][c]>(P[getLigne(idtemp)][getCol(idtemp)]+1) && utilise[l+1][c]==0 )
        P[l+1][c]=P[getLigne(idtemp)][getCol(idtemp)]+1;
    if ( c-1>=0  && P[l][c-1]>(P[getLigne(idtemp)][getCol(idtemp)]+1) && utilise[l][c-1]==0 )
        P[l][c-1]=P[getLigne(idtemp)][getCol(idtemp)]+1;
    if ( c+1<NB_COLONNES  && P[l][c+1]>(P[getLigne(idtemp)][getCol(idtemp)]+1) && utilise[l][c+1]==0 )
        P[l][c+1]=P[getLigne(idtemp)][getCol(idtemp)]+1;
    min=NB_LIGNES*NB_COLONNES;
    }

    

    return P[getLigne(id2)][getCol(id2)];
    
    
    }
    private int idPlayer()
    {
        return Entites.indexOf("P");
    }
    private void affichedecore() {
        for (int i = 0; i < NB_LIGNES; i++) {
            afficheEntitess(i);
            for (int j = 0; j < NB_COLONNES; j++) {
                if (decor[i][j] == 1)
                    System.out.print(sol);
                else if (decor[i][j] == 2)
                    System.out.print(echelle);
                else
                    System.out.print(vide);
            }
            System.out.println();
        }
        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < 4; i++) {
                {
                    System.out.print('|');
                    if (burger[i] >= 4 - k) {
                        System.out.print('~');
                        for (int j = 0; j < ((NB_COLONNES / 4) - 3); j++)

                            System.out.print(' ');
                    } else {
                        for (int j = 0; j < ((NB_COLONNES / 4) - 2); j++)

                            System.out.print(' ');
                    }
                }
                System.out.print('|');

            }

            System.out.println(" ");
        }
        for (int i = 0; i < NB_COLONNES; i++)
            System.out.print('—');

    }

    private void deplaceEnemie(int id) {
        int Lig = getLigne(id);
        int Col = getCol(id);
        if (getLigne(id)>=0 && getCol(id)-1>=0 && Entites.get(id - 1) == "0" && decor[Lig][Col - 1] == 1) {
            Entites.set(id - 1, Entites.get(id));
            Entites.set(id, "0");
        }
        if (getLigne(id)+2<NB_LIGNES && getCol(id)>=0 && Entites.get(getID(getLigne(id)+2, getCol(id))) == "0" && decor[Lig+1][Col] == 2) {
            Entites.set(getID(getLigne(id)+2, getCol(id)), Entites.get(id));
            Entites.set(id, "0");
            
        }
        else if (getLigne(id)>=0 && getCol(id)+1<NB_COLONNES && Entites.get(id + 1) == "0" && decor[Lig][Col + 1] == 1) {
            Entites.set(id + 1, Entites.get(id));
            Entites.set(id, "0");
        }
        else if (getLigne(id)-2>=0 && getCol(id)>=0 && Entites.get(getID(getLigne(id)-2, getCol(id))) == "0" && decor[Lig-1][Col] == 2) {
            Entites.set(getID(getLigne(id)-2, getCol(id)), Entites.get(id));
            Entites.set(id, "0");
        }
        
    }
    private void deplaceEnemmie(int id,String P)
    {
        int Lig = getLigne(id);
        int Col = getCol(id);
        int distmin=distMin(id, idPlayer());
        int idp=idPlayer();
        System.out.println(distmin);
        
        int idmin=id;
        
        if(getLigne(id)>=0 && getCol(id)-1>=0 && decor[Lig][Col - 1] == 1 && distmin > distMin(idPlayer(), id-1))
        {   distmin=distMin(idPlayer(), id-1);
            System.out.println("salut1"+distMin(idPlayer(), id-1));
            idmin=id-1;
        }
        else if(getLigne(id)>=0 && getCol(id)+1<NB_COLONNES && decor[Lig][Col + 1] == 1 && distmin > distMin(idPlayer(), id+1))
        {
            distmin=distMin(idPlayer(), id+1);
            System.out.println("salut2"+distMin(idPlayer(), id+1));
            idmin=id+1;
        }
        else if(getLigne(id)-2>=0 && getCol(id)>=0 && decor[Lig - 1][Col] == 2 && distmin > distMin(idPlayer(), getID(Lig-2, Col)))
        {
            distmin=distMin(idPlayer(), getID(Lig-2, Col));
            System.out.println("salut3"+distMin(idPlayer(), getID(Lig-2, Col)));
            idmin=getID(Lig-2, Col);
        }
        else if(getLigne(id)+2<NB_LIGNES && getCol(id)>=0 && decor[Lig + 1][Col] == 2 && distmin > distMin(idPlayer(), getID(Lig+2, Col)))
        {
            distmin=distMin(idPlayer(), getID(Lig+2, Col));
            System.out.println("salut4"+distMin(idPlayer(), getID(Lig+2, Col)));
            idmin=getID(Lig+2, Col);
        }
        Entites.set(idmin, P);
        if(idmin ==idp)
        Envie=false;
        Entites.set(id,"0");
    }

    private void deplaceEnemiesAll() {
        int id1=Entites.indexOf("E");
        int id2=Entites.lastIndexOf("E");
        if(enjeu() && Envie)
        
        deplaceEnemmie(id1,"E");
        if(enjeu() && Envie)
        deplaceEnemmie(id2,"E");
        
    }
    private void ennemietop(int id)
    {
        Entites.set(id, "0");
    }
    private void ennemiemake(int id,String P)
    {
        Entites.set(id,P);
    }
    

    public void jeu(String P) {
        Scanner s = new Scanner(System.in);
        char x = '0';
        
        while (Envie && enjeu()) {
            affichedecore();
            x = s.next().charAt(0);
            
            deplacerEntites(x, P);
            deplaceEnemiesAll();
            

            System.out.print("\033[H\033[2J");
            System.out.flush();

        }
        affichedecore();
        System.out.println("Vous avez perdu");
    }
}

class Info4B {
    /*
     * public static final String ANSI_RESET = "\u001B[0m"; public static final
     * String ANSI_BLACK = "\u001B[30m"; public static final String ANSI_RED =
     * "\u001B[31m"; public static final String ANSI_GREEN = "\u001B[32m"; public
     * static final String ANSI_YELLOW = "\u001B[33m"; public static final String
     * ANSI_BLUE = "\u001B[34m"; public static final String ANSI_PURPLE =
     * "\u001B[35m"; public static final String ANSI_CYAN = "\u001B[36m"; public
     * static final String ANSI_WHITE = "\u001B[37m";
     */
    public static void main(String[] args) {
        int NB_LIGNES = 11;
        int NB_COLONNES = 20;
        // 0==Vide, 1==Sol, 2==Echelle
        int[][] map = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2 },
                { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 2 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
                { 2, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0 },
                { 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

        List<String> Entites = new ArrayList<String>();
        for (int i = 0; i < NB_COLONNES * NB_LIGNES; i++) {
            Entites.add("0");
        }
        
        Entites.set(1, "P");
        Entites.set(86, "~");
        Entites.set(127, "~");
        Entites.set(207, "~");
        Entites.set(6, "~");
        
        Entites.set(43, "E");
        Entites.set(19, "E");

        Decore Tab = new Decore(map, Entites, NB_LIGNES, NB_COLONNES);

        Tab.jeu("P");
    }
}