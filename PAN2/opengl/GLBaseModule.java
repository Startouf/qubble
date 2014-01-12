import java.io.FileInputStream;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.GLU.*;


import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GLContext;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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

     Il est possible (recommandé) de passer aux exercices 3.1 -> 3.3 sur les shaders pour les groupes travaillant sur les shaders

     
     Ex 1.3: 
     - modifier cette routine pour afficher votre scène d'au moins deux points de vues en même temps, en partageant 
     votre zone d'affichage en plusieurs zones via glViewport. Vous devrez rendre votre scène une fois par point de 
     vue. Ceci vous sera utile dans votre projet pour gérer des zones d'affichages différentes, pour débugger votre contenu
     ou afficher des barres d'outils indépendantes de votre transformation de point de vue
     
     Ex 1.4: 
     - Sans changer votre caméra (gluLookat), faites tourner un cube sur lui-même
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
     pour l'utilisation de textures via les shaders, le tutoriel de lwjgl à ce sujet est très bien
     
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
     - En partant de votre cube, créez un VertexBufferObject (ou VBO, cf doc lwjgl) pour dessiner votre cube de couleur uniforme. Des routines de bases
     pour les VBO sont proposées dans cette classe.
     - définissez l'ensemble des triangles composants le cube dans un VBO, et dessinez le cube en utilisant la fonction glDrawArrays
     - définissez l'ensemble des points du cube dans un VBO, créez un VBO contenant les index définissant les triangles et dessinez 
     le cube en utilisant la fonction glDrawElements. Quelle différence faies-vous entre les deux methodes ?
     
     Ex 3.2:
     - En partant de l'exercice précedent
     - Ajoutez une couleur par face ou par sommet à votre cube.
     - Ajoutez une texture par face à votre cube.
     - Ajoutez une lumière à votre scène, et spécifiez les normales à votre cube.
     
     Ex 3.3:
     - en partant de votre cube précédent (VBO) et en vous référant à un tutoriel en ligne (par exemple http://lwjgl.org/wiki/index.php?title=GLSL_Shaders_with_LWJGL), creez un "program" openGL utilsant 
     un vertex shader et un fragment shader très simples pour dessiner votre objet. Des routines de bases pour les shaders sont proposées dans cette classe
     
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
    

    //à partir des exercices 3.1
    private int vertex_vbo_id = 0;
    private int color_vbo_id = 0;
    private int index_vbo_id = 0;
    private int nb_indices = 0;
    private boolean use_vbo = false; 
    private boolean use_shader = false; 
    
    private int program;
    private int vertexShader;
    private int fragmentShader;    

    
    public GLBaseModule() {
    }

	private static FloatBuffer makeFloatBuffer(float[] values)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}


    public void Run(){
        initDisplay();
        
        initGL();
        
        initShaders();
        
        while(do_run){
            if(Display.isCloseRequested())
                do_run=false;
            
            render();
            
            Display.update();
        }
        
        Display.destroy();
    }
    
    
    private void render(){
        float w, h;
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);        
        
        if (use_vbo) {
            draw_triangle_vbo(1f, 1f, 1f);
        } else {
            draw_triangle(1f, 1f, 1f);
        }
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
        /*Matrice de projection (3D vers 2D): utilisation d'une projection en ortho*/
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(-1f, 1f, -1f, 1f, -10, 10);
        
      
        /*Matrice de modele (e.g. positionnement initial de la "camera" )*/
        GL11.glMatrixMode(GL11.GL_MODELVIEW);        
        GL11.glLoadIdentity();
        
        /*Diverses options OpenGL*/
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK,GL11.GL_AMBIENT);
        
        GL11.glEnable(GL11.GL_LIGHT0);
    	GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, makeFloatBuffer( new float[]{ 0f, 0f	, 1.0f, 0.0f } )  );
     	GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, makeFloatBuffer(new float[]{1f, 0f, 0f,1f}));       
        GL11.glEnable(GL11.GL_LIGHTING);
        
    }
        
    private void draw_triangle(float w, float h, float d)
    {
        w/=2;
        h/=2;
        d/=2;
        
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glNormal3f(0f,0f,1f);
        GL11.glVertex3f(w, h, d);
        GL11.glVertex3f(w, -h, d);
        GL11.glVertex3f(-w, h, d);


        GL11.glEnd();
    }

    
    //
    //  Fonctions pour la manipulations de VBO
    //

    private void draw_triangle_vbo(float w, float h, float d)
    {
        load_triangle_vbo(w, h, d);      
        draw_vbos();
    }
    
    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
    
    public static int createVBOID() {
      if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        ARBVertexBufferObject.glGenBuffersARB(buffer);
        return buffer.get(0);
      }
      return 0;
    }
    
    private int load_float_vbo(float [] values)
    {
      //chargeons les données dans un FloatBuffer
  		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(values.length);
	   	verticesBuffer.put(values);
	   	verticesBuffer.flip(); //NE PAS OUBLIER CETTE LIGNE!! ELLE PEUT CRASHER VOTRE JavaVM
      
      //creons un VBO dans la mémoire du GPU (pas encore de données associées))
      int vbo_id = createVBOID();
      
      //et copions les données dans la mémoire du GPU
  		if(GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vbo_id);
        ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, verticesBuffer, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
      } else {
        System.out.println("VBOs NOT SUPPORTED !!");
      }
      return vbo_id;
    }
    
    private int load_index_vbo(int [] values)
    {
      //chargeons les données dans un IntBuffer
  		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(values.length);
	   	indicesBuffer.put(values);
	   	indicesBuffer.flip(); //NE PAS OUBLIER CETTE LIGNE!! ELLE PEUT CRASHER VOTRE JavaVM
      
      //creons un VBO dans la mémoire du GPU (pas encore de données associées))
      int vbo_id = createVBOID();
      
      //et copions les données dans la mémoire du GPU
  		if(GLContext.getCapabilities().GL_ARB_vertex_buffer_object) {
        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vbo_id);
        ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indicesBuffer, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
      } else {
        System.out.println("VBOs NOT SUPPORTED !!");
      }
      
      //souvenons nous du nombre d'index dans notre VBO:
      nb_indices = values.length;      
      return vbo_id;
    }
    
    
    private void load_triangle_vbo(float w, float h, float d) {
      //nous avons déja crée le VBO, inutile de recommencer ...
      if (vertex_vbo_id != 0) return;

      //creons un VBO qui contient nos vertex - nous avons besoin de 3 sommets
      w/=2;
      h/=2;
      d/=2;
      float[] vertices = new float[] {
        -w, h, d,
        w, h, d,
        w, -h, d,
      };
      vertex_vbo_id = load_float_vbo(vertices);
      
      //creons un VBO qui contient les index des sommets dans les deux triangles de notre cube
      int[] indices = new int[]
      {
        0,1,2
      };
      index_vbo_id = load_index_vbo(indices);

      System.out.println("VBOs Setup - Vertex ID " + vertex_vbo_id + " - index ID " + index_vbo_id + " - nb indices " + nb_indices);
    }
    
    private void draw_vbos() {
      if (vertex_vbo_id==0) return;
      if (index_vbo_id==0) return;
      
      if (use_shader) {
        ARBShaderObjects.glUseProgramObjectARB(program);
      }
      
      GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
      ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertex_vbo_id);
      GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

      if (color_vbo_id != 0) {
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, color_vbo_id);
        GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 0);
      }

      //attachons le buffer d'indices comme le buffer 'ELEMENT_ARRAY', i.e. celui utilisé pour glDrawElements
      ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, index_vbo_id);
  		
      GL11.glDrawElements(GL11.GL_TRIANGLES, nb_indices, GL11.GL_UNSIGNED_INT, 0);

      GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
      if (color_vbo_id != 0) GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);

      if (use_shader) {
        ARBShaderObjects.glUseProgramObjectARB(0);
      }
    }

    
    //
    //  Shader init
    //
    private void initShaders() {
        /*init openGL shaders*/
        program = ARBShaderObjects.glCreateProgramObjectARB();
        if (program==0) {
            System.out.println("Error: OpenGL shaders not supported");
            System.exit(0);
        }
        vertexShader=ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
        String vertexCode=""+
        "void main(void)" + 
        "{" + 
        "	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;" +  
        "}";
        
        ARBShaderObjects.glShaderSourceARB(vertexShader, vertexCode);
        ARBShaderObjects.glCompileShaderARB(vertexShader);
        if (ARBShaderObjects.glGetObjectParameteriARB(vertexShader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.out.println("Vertex shader not compiled: " + getLogInfo(vertexShader) );
        }
        
        fragmentShader=ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        String fragmentCode="" +
        "void main(void) {" +
        "	gl_FragColor = vec4 (0.0, 1.0, 0.0, 1.0);" +
        "}";
        
        ARBShaderObjects.glShaderSourceARB(fragmentShader, fragmentCode);
        ARBShaderObjects.glCompileShaderARB(fragmentShader);
        if (ARBShaderObjects.glGetObjectParameteriARB(fragmentShader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
        {
            System.out.println("Fragment shader not compiled: " + getLogInfo(fragmentShader) );
        }
        
        
        ARBShaderObjects.glAttachObjectARB(program, vertexShader);
        ARBShaderObjects.glAttachObjectARB(program, fragmentShader);
        ARBShaderObjects.glLinkProgramARB(program);
        ARBShaderObjects.glValidateProgramARB(program);
    }
    
    
    
    public static void main(String[] args){
        GLBaseModule app = new GLBaseModule();
        /*parse our args*/
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("-vbo")) {
                app.use_vbo = true;
            }
            else if (args[i].equals("-shader")) {
                app.use_shader = true;
            }
        }
        
        app.Run();
    }    
    
}
