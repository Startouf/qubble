package opengl;
import java.io.FileInputStream;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ARBFragmentShader;


public class GLBaseModule {

    private boolean do_run=true; //runs until done is set to true
    private int display_width = 640;
    private int display_height = 480;
    
        /* Les exercices nécessiteront une restructuration (potentiellement complète) des routines initGL et render ci-dessous

        La documentation de lwjgl est disponible en ligne: http://lwjgl.org/wiki/index.php?title=Main_Page#Getting_started
        La documentation de lwjgl est de très bonne qualité, vou devriez pouvoir effectuer ces exercices très rapidement !

        Il existe par ailleurs de très nombreux site traitant de la programmation OpenGL, dont le fameux Red Book:
            http://www.glprogramming.com/red/
        ainsi que le très bon site en francais
          http://www.ozone3d.net/tutorials/        
        N'oubliez pas de consulter les liens bibliographiques disponible sur PACT:
          http://pact.enst.fr/composants/blocs-fonctionnels/la-3d/synthese-3d/
                  
        Phase 0: Biblio OpenGL
          Cette biblio se fera tout au long de votre module, et comprendra de manière succincte:
            - Explication du modèle OpenGL pour le fonctionnement du processeur graphique: quels sont les blocs fonctionnels du GPU et
              à quoi servent-ils
            - Principes de base de OpenGL: Représentation des données et des transformations géometriques: Matrices, vertex, lignes/triangles/..., projection et modèle de caméra
            - Principes de base de OpenGL: Texture et système de coordonnées
            - Principes de base de OpenGL: Modèle de lumière            
            - Principes de base de OpenGL: Expliquez les différences entre DisplayList, VeretxArray et VertexBufferObject

        Phase 1: Manipulation de base en OpenGL
          Ex 1.1: 
            - utilisez une projection perspective au lieu d'une projection orthogonale
            - expliquez la différence entre les modes de projections

          Ex 1.2: 
            - Dessinez un carré
            - Dessinez un carré à l'aide d'un "éventail" de triangles (Triangle Fan), puis à l'aide d'un "ruban" (Triangle Strip)
            - Dessinez un cube au lieu d'un triangle, avec une couleur par face, puis une sphère ou tout autre objet

          Ex 1.3: 
            - modifier cette routine pour afficher votre scène d'au moins deux points de vues en même temps, en partageant 
            votre zone d'affichage en plusieurs zones via glViewport. Vous devrez rendre votre scène une fois par point de 
            vue. Ceci vous sera utile dans votre projet pour gérer des zones d'affichages différentes, pour débugger votre contenu
            ou afficher des barres d'outils indépendantes de votre transformation de point de vue
            
          Ex 1.4: 
            - Sans changer votre caméra, faites tourner un cube sur lui-même
              - manuellement via le clavier (flêches de direction)
              - de maniere automatique en faisant un tour par seconde
            Vous ferez pour cela un contrôle de la vitesse de rafraichissement en décidant de la fréquence des trames affichées. Ceci est 
            classiquement appelé "frame rate", "FPS" ou "Frame Per Second"? Les jeux utilisent classiquement des fréquences de trames 
            entre 30 et 60Hz.
            Votre rotation se fera à l'aide en modifiant la matrice de modèle courante via une matrice de rotation.

          Ex 1.5: 
            - Sans changer les coordonnées de vos objets, animer le point de vue de votre caméra virtuelle
              - manuellement via le clavier (fleches de direction)
              - de maniere automatique en faisant un tour par seconde

        Phase 2: Manipulation de l'aspect des objets
          Ex 2.1: 
           - chargez une image à l'aide de l'objet TextureLoader et dessinez l'image sous forme d'un rectangle
            !! vous ferez attention à ne pas déformer l'image

          Ex 2.2: 
           - chargez 3 images différentes et utilisez les pour remplir les faces d'un cube

          Ex 2.3: 
           - Petit travail biblio: comment marche le modèle de lumière en OpenGL. Faites en particulier attention à vos définitions de normales !
           - Placez une lumière dans votre scène (sans texture) éclairant un cube placé au centre de la scène
              - modifiez les parametres de materiel pour changer l'apparence de votre cube
              - faites tourner votre cube ou votre lumière pour visualiser les changements liés à l'éclairage.
              
          Ex 2.4:
            - Sans modifier vos paramètres de lumières (source de lumière et materiel de l'objet), 
              animer légèrement la normale de chaque face du cube (une légère rotation autour de la vraie normale)
              et observer le résultat

        Phase 3: Vers plus de puissance et de souplesse!!
            Les exercices que vous avez effectués jusqu'ici utilise le modèle d'origine d'OpenGL, souvent appelé "fixed pipeline": 
            vous ne pouvez pas modifier la manière dont votre carte graphique dessine un pixel. 
            Nous allons étudier les GPU modernes qui permettent ce genre de manipulation. 

          Ex 3.1:
            - En partant de votre cube, créez un VertexBufferObject (ou VBO, cf doc lwjgl) pour dessiner votre cube de couleur uniforme.
                - définissez l'ensemble des triangles composants le cube dans un VBO, et dessinez le cube en utilisant la fonction glDrawArrays
                - définissez l'ensemble des points du cube dans un VBO, créez un VBO contenant les index définissant les triangles et dessinez 
                  le cube en utilisant la fonction glDrawElements
                            
          Ex 3.2:
            - En partant de l'exercice précedent
            - Ajoutez une couleur par face ou par sommet à votre cube.
            - Ajoutez une texture par face à votre cube.
            - Ajoutez une lumière à votre scène, et spécifiez les normales à votre cube.
          
          Ex 3.3:
            - en partant de votre cube précédent (VBO) et en vous référant à un tutoriel en ligne (par exemple http://lwjgl.org/wiki/index.php?title=GLSL_Shaders_with_LWJGL), creez un "program" openGL utilsant 
            un vertex shader et un fragment shader très simples pour dessiner votre objet.

          Ex 3.4:
            - en partant de l'exemple précédent, animer votre cube SANS CHANGER LE VBO pour:
              - modifier très légèrement les coins du cube dans le vertex shader à partir d'un paramètre externe (lu depuis le shader mais défini dans votre programme java)
              - modifier très légèrement les couleurs du cube dans le fragment shader à partir d'un paramètre externe (lu depuis le shader mais défini dans votre programme java)
              ou d'un paramètre calculé dans le vertex shader, comme un vecteur normal altéré

          Ex 3.5:
            - en partant de l'exemple 3.3, utiliser les VBO pour ajouter une texture à votre cube (le tutoriel de lwjgl à ce sujet est très bien)

          Phase 4: 
            Selon les groupes, nous ajusterons les étapes suivantes en fonction des besoins
        */
        
        
    public GLBaseModule(){
        initDisplay();
        
        initGL();
        
        while(do_run){
            if(Display.isCloseRequested())
              do_run=false;

            render();

            Display.update();
        }

        Display.destroy();
    }

    private void render(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        //dessiner un triangle rouge        
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex2f(display_width/2, display_height/2);
        GL11.glVertex2f(display_width, display_height/2);
        GL11.glVertex2f(display_width/2, display_height);

        GL11.glEnd();
    }

    private void initDisplay(){    
        try{
            //Creation d'une fenetre permettant de dessiner avec OpenGL
            Display.setDisplayModeAndFullscreen(new DisplayMode(display_width, display_height) );
            Display.create();

            DisplayMode mode = Display.getDisplayMode();
        }catch(Exception e){
            System.out.println("Error setting up display: "+ e.getMessage());
            System.exit(0);
        }
    }

    private void initGL(){    
        /*GL viewport: nous utilisons toute la fenetre pour dessiner*/
        GL11.glViewport(0, 0, display_width, display_height);
        /*Matrice de projection (3D vers 2D): utilisationd'une projection orthogonale*/
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, display_width, display_height, 0, 1, -1);

        /*Matrice de modele (e.g. positionnement initial de la "camera" )*/
        GL11.glMatrixMode(GL11.GL_MODELVIEW);        
        GL11.glLoadIdentity();

        /*Diverses options OpenGL*/
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
    }

    public static void main(String[] args){
        new GLBaseModule();
    }
}
