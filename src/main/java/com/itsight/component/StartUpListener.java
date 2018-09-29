package com.itsight.component;

import com.itsight.domain.*;
import com.itsight.domain.Musculo;
import com.itsight.domain.Objetivo;
import com.itsight.domain.Rol;
import com.itsight.domain.jsonb.*;
import com.itsight.repository.BagForestRepository;
import com.itsight.repository.SecurityUserRepository;
import com.itsight.repository.TipoDescuentoRepository;
import com.itsight.service.*;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.util.*;

@Component
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SecurityUserRepository userRepository;

    @Autowired
    private ParametroService parametroService;

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @Autowired
    private PlanService planService;

    @Autowired
    private TipoImagenService tipoImagenService;

    @Autowired
    private ObjetivoService objetivoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private TipoAudioService tipoAudioService;

    @Autowired
    private ServletContext context;

    @Autowired
    private TipoDescuentoRepository tipoDescuentoRepository;

    @Autowired
    private CategoriaEjercicioService categoriaEjercicioService;

    @Autowired
    private SubCategoriaEjercicioService subCategoriaEjercicioService;

    @Autowired
    private EspecificacionSubCategoriaService especificacionSubCategoriaService;
//

    @Autowired
    private GrupoVideoService grupoVideoService;

    @Autowired
    private CategoriaVideoService categoriaVideoService;

    @Autowired
    private SubCategoriaVideoService subCategoriaVideoService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private AudioService audioService;

    @Autowired
    private MusculoService musculoService;

    @Autowired
    private CapacidadMejoraService capacidadMejoraService;

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RedFitnessService redFitnessService;

    @Autowired
    private KilometrajeBaseService kilometrajeBaseService;

    @Autowired
    private PorcentajesKilometrajeService porcentajesKilometrajeService;

    @Autowired
    private UsuarioFitnessService usuarioFitnessService;

    @Value("${main.repository}")
    private String mainRoute;

    private final Long currentVersion = new Date().getTime();

    private static int index = 1;

    @Autowired
    private BagForestRepository bagForestRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        //Nos aseguramos que si los seeders ya han sido ejecutados nos saltemos su registro
        if(tipoDocumentoService.findOne(1) == null){
            //Main Seeders
            addingObjectivesTable();
            addingCategoriaTable();
            addingImageTypeTable();
            addingProfilesToTable();
            addingRolToTable();
            addingPlanToTable();
            addingToTipoDescuentoTable();
            addingTipoAudioToTable();
            addingMusculoToTable();
            addingCondicionMejoraToTable();
            addingTipoDocumentoToTable();
            addingKilometrajBase();
            addingAudiosDemo();

            if(bagForestRepository.findOne(1) == null) {
                insertACategoriaEjercicio();
                insertASubCategoriaEjercicio();
                insertAEspecificacionSubCategoriaEjercicio();
            }

            if(bagForestRepository.findOne(2) == null) {
                insertAGrupoVideo();
                insertACategoriaVideo();
                insertASubCategoriaVideo();
                insertAVideo();
            }
        }

        addingApplicationParameters();
        addingToContextSession();
        addingInitUsers();
        creatingFileDirectories();
    }


    public void insertACategoriaEjercicio() {
        subCategoriaEjercicioService.insertaArtificio();
        if(categoriaEjercicioService.findOne(1) == null) categoriaEjercicioService.save(
            new CategoriaEjercicio("Velocidad",1, true, "/1/16817987-3932-41f2-afa7-3e60911d3f86.png", "C://WorkoutAppRepository/CategoriasEjercicio/1/16817987-3932-41f2-afa7-3e60911d3f86.png", UUID.fromString("16817987-3932-41f2-afa7-3e60911d3f86")));
        if(categoriaEjercicioService.findOne(2) == null) categoriaEjercicioService.save(
            new CategoriaEjercicio("Fuerza",1, true, "/2/b46b3753-b9c8-4218-97b4-4b579b74d9ce.png", "C://WorkoutAppRepository/CategoriasEjercicio/2/b46b3753-b9c8-4218-97b4-4b579b74d9ce.png", UUID.fromString("b46b3753-b9c8-4218-97b4-4b579b74d9ce")));
        if(categoriaEjercicioService.findOne(3) == null) categoriaEjercicioService.save(
            new CategoriaEjercicio("Resistencia",1, true, "/3/53454de7-b6a5-40f8-b0c5-eba7660b2712.png", "C://WorkoutAppRepository/CategoriasEjercicio/3/53454de7-b6a5-40f8-b0c5-eba7660b2712.png", UUID.fromString("53454de7-b6a5-40f8-b0c5-eba7660b2712")));
        if(categoriaEjercicioService.findOne(4) == null) categoriaEjercicioService.save(
            new CategoriaEjercicio("Flexibilidad",1, true, "/4/19a46a87-1350-4880-b01d-1165bd42ce34.png", "C://WorkoutAppRepository/CategoriasEjercicio/4/19a46a87-1350-4880-b01d-1165bd42ce34.png", UUID.fromString("19a46a87-1350-4880-b01d-1165bd42ce34")));
        if(categoriaEjercicioService.findOne(5) == null) categoriaEjercicioService.save(
            new CategoriaEjercicio("Abdominales",1, true, "/5/73631f11-ae6c-47fa-9955-8249d497e69a.png", "C://WorkoutAppRepository/CategoriasEjercicio/5/73631f11-ae6c-47fa-9955-8249d497e69a.png", UUID.fromString("73631f11-ae6c-47fa-9955-8249d497e69a")));
        if(categoriaEjercicioService.findOne(6) == null) categoriaEjercicioService.save(
            new CategoriaEjercicio("Calentamiento",1, true, "/6/ea8fae11-7b03-402f-b1e4-91a0c8a3d90a.png", "C://WorkoutAppRepository/CategoriasEjercicio/6/ea8fae11-7b03-402f-b1e4-91a0c8a3d90a.png", UUID.fromString("ea8fae11-7b03-402f-b1e4-91a0c8a3d90a")));
        if(categoriaEjercicioService.findOne(7) == null) categoriaEjercicioService.save(
            new CategoriaEjercicio("Técnica",1, true, "/7/b66ba859-152d-4535-986f-78aa68859e78.png", "C://WorkoutAppRepository/CategoriasEjercicio/7/b66ba859-152d-4535-986f-78aa68859e78.png", UUID.fromString("b66ba859-152d-4535-986f-78aa68859e78")));
        if(categoriaEjercicioService.findOne(8) == null) categoriaEjercicioService.save(
            new CategoriaEjercicio("Otros",1, true, "/8/ac92e363-24d6-4f27-bee2-fdcba7988a4b.png", "C://WorkoutAppRepository/CategoriasEjercicio/8/ac92e363-24d6-4f27-bee2-fdcba7988a4b.png", UUID.fromString("ac92e363-24d6-4f27-bee2-fdcba7988a4b")));
    }

    public void insertASubCategoriaEjercicio() {
        if(subCategoriaEjercicioService.findOne(1) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Velocidad",1, true));
        if(subCategoriaEjercicioService.findOne(2) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Resistencia especial",1, true));
        if(subCategoriaEjercicioService.findOne(3) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Pre competitivos",1, true));
        if(subCategoriaEjercicioService.findOne(4) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Spinning",1, true));
        if(subCategoriaEjercicioService.findOne(5) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Fuerza",2, true));
        if(subCategoriaEjercicioService.findOne(6) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Resistencia a la fuerza",2, true));
        if(subCategoriaEjercicioService.findOne(7) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Potencia",2, true));
        if(subCategoriaEjercicioService.findOne(8) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Estándar",3, true));
        if(subCategoriaEjercicioService.findOne(9) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Spinning",3, true));
        if(subCategoriaEjercicioService.findOne(10) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Gluteos",4, true));
        if(subCategoriaEjercicioService.findOne(11) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Abductores",4, true));
        if(subCategoriaEjercicioService.findOne(12) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Aductores",4, true));
        if(subCategoriaEjercicioService.findOne(13) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Banda iliotibial",4, true));
        if(subCategoriaEjercicioService.findOne(14) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Pantorrillas",4, true));
        if(subCategoriaEjercicioService.findOne(15) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Sóleo y tendón de Aquiles",4, true));
        if(subCategoriaEjercicioService.findOne(16) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Recto y Psoas",4, true));
        if(subCategoriaEjercicioService.findOne(17) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Extensores de la espalda a la fuerza",4, true));
        if(subCategoriaEjercicioService.findOne(18) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Estándar",4, true));
        if(subCategoriaEjercicioService.findOne(19) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Isquiotibiales",4, true));
        if(subCategoriaEjercicioService.findOne(20) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Cuádriceps",4, true));
        if(subCategoriaEjercicioService.findOne(21) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Estándar",5, true));
        if(subCategoriaEjercicioService.findOne(21) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Básicos",6, true));
        if(subCategoriaEjercicioService.findOne(22) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Intermedios",6, true));
        if(subCategoriaEjercicioService.findOne(23) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Avanzados",6, true));
        if(subCategoriaEjercicioService.findOne(24) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Pre-competitivo",6, true));
        if(subCategoriaEjercicioService.findOne(25) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("En el sitio",6, true));
        if(subCategoriaEjercicioService.findOne(26) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Longitud de paso",7, true));
        if(subCategoriaEjercicioService.findOne(27) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Cadencia",7, true));
        if(subCategoriaEjercicioService.findOne(28) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Centro de gravedad",7, true));
        if(subCategoriaEjercicioService.findOne(29) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Pisada",7, true));
        if(subCategoriaEjercicioService.findOne(30) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Zancada",7, true));
        if(subCategoriaEjercicioService.findOne(31) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Braceo",7, true));
        if(subCategoriaEjercicioService.findOne(32) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Coordinativos",7, true));
        if(subCategoriaEjercicioService.findOne(33) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Potencia",7, true));
        if(subCategoriaEjercicioService.findOne(34) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Fuerza",7, true));
        if(subCategoriaEjercicioService.findOne(35) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Posturales",7, true));
        if(subCategoriaEjercicioService.findOne(36) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Básicos",7, true));
        if(subCategoriaEjercicioService.findOne(37) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Spinning",8, true));
        if(subCategoriaEjercicioService.findOne(38) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Post maratón",8, true));
        if(subCategoriaEjercicioService.findOne(39) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Fortalecimiento de tobillo",8, true));
        if(subCategoriaEjercicioService.findOne(40) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Propioceptivas",8, true));
    }

    public void insertAEspecificacionSubCategoriaEjercicio(){
        for(SubCategoriaEjercicio sce:subCategoriaEjercicioService.findAllByOrderById()){
            for(int i=1; i<4;i++){
                if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("Acondicionamiento", sce.getId(),i, true));
                if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("General",sce.getId(),i, true));
                if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("Específica",sce.getId(),i, true));
                if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("Precompetitiva",sce.getId(),i, true));
                if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("Competitiva",sce.getId(),i, true));
            }
        }
    }

    public void insertAGrupoVideo(){
        categoriaVideoService.insertArtificio();
        if(grupoVideoService.findOne(1) == null) grupoVideoService.save(
                new GrupoVideo("Fuerza tren inferior",2, true, "/1/633ec781-16c8-43a1-821e-23db3c30e318.png", "C://WorkoutAppRepository/GruposVideo/1/633ec781-16c8-43a1-821e-23db3c30e318.png", UUID.fromString("633ec781-16c8-43a1-821e-23db3c30e318")));
        if(grupoVideoService.findOne(2) == null) grupoVideoService.save(
                new GrupoVideo("Fuerza tren superior",2, true, "/2/04472cde-a6df-4efa-bfb3-e46f9c8be954.png", "C://WorkoutAppRepository/GruposVideo/2/04472cde-a6df-4efa-bfb3-e46f9c8be954.png", UUID.fromString("04472cde-a6df-4efa-bfb3-e46f9c8be954")));
        if(grupoVideoService.findOne(3) == null) grupoVideoService.save(
                new GrupoVideo("Técnicos",2, true, "/3/1ee8d403-aec5-4fbf-9b55-49fd964d6824.png", "C://WorkoutAppRepository/GruposVideo/3/1ee8d403-aec5-4fbf-9b55-49fd964d6824.png", UUID.fromString("1ee8d403-aec5-4fbf-9b55-49fd964d6824")));
        if(grupoVideoService.findOne(4) == null) grupoVideoService.save(
                new GrupoVideo("Flexibilidad - Movilidad - Masajes",2, true, "/4/d6d68b6e-9038-4c98-9e4b-abe47b81a137.png", "C://WorkoutAppRepository/GruposVideo/4/d6d68b6e-9038-4c98-9e4b-abe47b81a137.png", UUID.fromString("d6d68b6e-9038-4c98-9e4b-abe47b81a137")));
        if(grupoVideoService.findOne(5) == null) grupoVideoService.save(
                new GrupoVideo("Propioceptivos",2, true, "/5/fb216603-c9c7-4171-8cad-46bd318a1eba.png", "C://WorkoutAppRepository/GruposVideo/5/fb216603-c9c7-4171-8cad-46bd318a1eba.png", UUID.fromString("fb216603-c9c7-4171-8cad-46bd318a1eba")));
    }

    public void insertACategoriaVideo() {
        if(categoriaVideoService.findOne(1) == null) categoriaVideoService.save(new CategoriaVideo("Cuádriceps",1, true));
        if(categoriaVideoService.findOne(2) == null) categoriaVideoService.save(new CategoriaVideo("Glúteos",1, true));
        if(categoriaVideoService.findOne(3) == null) categoriaVideoService.save(new CategoriaVideo("Glúteo Medio",1, true));
        if(categoriaVideoService.findOne(4) == null) categoriaVideoService.save(new CategoriaVideo("Isquiotibiales",1, true));
        if(categoriaVideoService.findOne(5) == null) categoriaVideoService.save(new CategoriaVideo("Flexóres de la cadera",1, true));
        if(categoriaVideoService.findOne(6) == null) categoriaVideoService.save(new CategoriaVideo("Pliométricos",1, true));
        if(categoriaVideoService.findOne(7) == null) categoriaVideoService.save(new CategoriaVideo("Tríceps sural",1, true));
        if(categoriaVideoService.findOne(8) == null) categoriaVideoService.save(new CategoriaVideo("Tibiales",1, true));
        if(categoriaVideoService.findOne(9) == null) categoriaVideoService.save(new CategoriaVideo("Tobillos",1, true));
        if(categoriaVideoService.findOne(10) == null) categoriaVideoService.save(new CategoriaVideo("Plantares",1, true));
        if(categoriaVideoService.findOne(11) == null) categoriaVideoService.save(new CategoriaVideo("Rotadores",1, true));
        if(categoriaVideoService.findOne(12) == null) categoriaVideoService.save(new CategoriaVideo("B. Iliotibial",1, true));

        if(categoriaVideoService.findOne(13) == null) categoriaVideoService.save(new CategoriaVideo("Cuello",2, true));
        if(categoriaVideoService.findOne(14) == null) categoriaVideoService.save(new CategoriaVideo("Deltoides",2, true));
        if(categoriaVideoService.findOne(15) == null) categoriaVideoService.save(new CategoriaVideo("Espalda",2, true));
        if(categoriaVideoService.findOne(16) == null) categoriaVideoService.save(new CategoriaVideo("Pectorales",2, true));
        if(categoriaVideoService.findOne(17) == null) categoriaVideoService.save(new CategoriaVideo("Flaxóres del codo tríceps",2, true));
        if(categoriaVideoService.findOne(18) == null) categoriaVideoService.save(new CategoriaVideo("Flaxóres de la cadera recto",2, true));
        if(categoriaVideoService.findOne(19) == null) categoriaVideoService.save(new CategoriaVideo("Oblicuos",2, true));
        if(categoriaVideoService.findOne(20) == null) categoriaVideoService.save(new CategoriaVideo("Transverso",2, true));

        if(categoriaVideoService.findOne(21) == null) categoriaVideoService.save(new CategoriaVideo("Zancada",3, true));
        if(categoriaVideoService.findOne(22) == null) categoriaVideoService.save(new CategoriaVideo("Coordinativos Movilidad",3, true));
        if(categoriaVideoService.findOne(23) == null) categoriaVideoService.save(new CategoriaVideo("Potencia",3, true));
        if(categoriaVideoService.findOne(24) == null) categoriaVideoService.save(new CategoriaVideo("Postulares",3, true));
        if(categoriaVideoService.findOne(25) == null) categoriaVideoService.save(new CategoriaVideo("Braceo",3, true));

        if(categoriaVideoService.findOne(26) == null) categoriaVideoService.save(new CategoriaVideo("Cuello",4, true));
        if(categoriaVideoService.findOne(27) == null) categoriaVideoService.save(new CategoriaVideo("Brazos",4, true));
        if(categoriaVideoService.findOne(28) == null) categoriaVideoService.save(new CategoriaVideo("Lumbar",4, true));
        if(categoriaVideoService.findOne(29) == null) categoriaVideoService.save(new CategoriaVideo("Caderas",4, true));
        if(categoriaVideoService.findOne(30) == null) categoriaVideoService.save(new CategoriaVideo("Rodillas",4, true));
        if(categoriaVideoService.findOne(31) == null) categoriaVideoService.save(new CategoriaVideo("Tobillos",4, true));
        if(categoriaVideoService.findOne(32) == null) categoriaVideoService.save(new CategoriaVideo("Cadenas",4, true));
        if(categoriaVideoService.findOne(33) == null) categoriaVideoService.save(new CategoriaVideo("Dinámicos",4, true));
        if(categoriaVideoService.findOne(33) == null) categoriaVideoService.save(new CategoriaVideo("Masajes",4, true));
    }

    public void insertASubCategoriaVideo(){
        int index = 1;
        for(CategoriaVideo sce: categoriaVideoService.findAll()){
            int i = sce.getId();
            if(subCategoriaVideoService.findOne(index++) == null) subCategoriaVideoService.save(new SubCategoriaVideo("Concéntricos",i, true));
            if(subCategoriaVideoService.findOne(index++) == null) subCategoriaVideoService.save(new SubCategoriaVideo("Excéntricos",i, true));
            if(subCategoriaVideoService.findOne(index++) == null) subCategoriaVideoService.save(new SubCategoriaVideo("Isométricos",i, true));
            if(subCategoriaVideoService.findOne(index++) == null) subCategoriaVideoService.save(new SubCategoriaVideo("Propiocepticos",i, true));
            if(subCategoriaVideoService.findOne(index++) == null) subCategoriaVideoService.save(new SubCategoriaVideo("Téc. concéntricos",i, true));
            if(subCategoriaVideoService.findOne(index++) == null) subCategoriaVideoService.save(new SubCategoriaVideo("Téc. Pliométricos",i, true));
        }
    }

    public void insertAVideo(){
        final HashMap<Integer, String> mapEjercicios = new HashMap<>();
        mapEjercicios.put(1, "Elevaciones de tronco en el suelo");
        mapEjercicios.put(2, "Elevaciones de piernas extendidas en suelo");
        mapEjercicios.put(3, "Elevaciones de talones de pie");
        mapEjercicios.put(4, "Burpess");
        mapEjercicios.put(5, "Saltos del patinador");
        mapEjercicios.put(6, "Sentadillas frontales");
        mapEjercicios.put(7, "Flexiones diamante");
        mapEjercicios.put(8, "Flexiones de brazos tradicionales");
        mapEjercicios.put(9, "Crab walk");
        mapEjercicios.put(10, "Bear crawl o paso del oso");

        int index = 1;
        for(SubCategoriaVideo sce: subCategoriaVideoService.findAll()){
            int i = sce.getId();
            Random rd = new Random();
            int iteraciones = rd.nextInt(10)+1;
            for(int ii=0; ii<iteraciones;ii++){
                int randomInt = rd.nextInt(10)+1;
                if(videoService.findOne(index++) == null) videoService.save(new Video(mapEjercicios.get(randomInt), "/1/36a25880-ed66-47f1-ac79-8d2ee5fbf418.mp4", "C://WorkoutAppRepository/Videos/1/36a25880-ed66-47f1-ac79-8d2ee5fbf418.mp4","2.19 MB","00:00:14", UUID.fromString("36a25880-ed66-47f1-ac79-8d2ee5fbf418"), i, true));
            }
        }
    }

    public void addingObjectivesTable() {
        if (objetivoService.findOne(1) == null) {
            objetivoService.save(new Objetivo("Velocidad", "Este objetivo busca principalmente mejorar la velocidad del runner", true));
        }
        if (objetivoService.findOne(2) == null) {
            objetivoService.save(new Objetivo("Resistencia", "Este objetivo busca principalmente mejorar la resistencia del runner", true));
        }
        if (objetivoService.findOne(3) == null) {
            objetivoService.save(new Objetivo("Supera tus límites", "Este objetivo busca consolidar los objetivos inicial retando al runner mediante runitas mucho más intensas que las normales", true));
        }
    }

    public void addingCategoriaTable() {
        if (categoriaService.findOne(1) == null) {
            categoriaService.save(new Categoria(0, "Performance", "Performance", true, "", "", null));
        }
        if (categoriaService.findOne(2) == null) {
            categoriaService.save(new Categoria(0, "Técnica", "Técnica", true, "", "", null));
        }
        if (categoriaService.findOne(3) == null) {
            categoriaService.save(new Categoria(0, "Fortalecimiento", "Fortalecimiento", true, "", "", null));
        }
        if (categoriaService.findOne(4) == null) {
            categoriaService.save(new Categoria(0, "Metodología", "Metodología", true, "", "", null));
        }
    }

    public void addingImageTypeTable() {
        if (tipoImagenService.findOneById(1) == null) {
            tipoImagenService.add(new TipoImagen("Desktop_Home", 500.0, 500.0));
        }
        if (tipoImagenService.findOneById(2) == null) {
            tipoImagenService.add(new TipoImagen("Responsive_Home", 600.0, 700.0));
        }
        if (tipoImagenService.findOneById(3) == null) {
            tipoImagenService.add(new TipoImagen("Desktop_Plan", 800.0, 800.0));
        }
        if (tipoImagenService.findOneById(4) == null) {
            tipoImagenService.add(new TipoImagen("Desktop_Plan_Rollover", 900.0, 900.0));
        }
        if (tipoImagenService.findOneById(5) == null) {
            tipoImagenService.add(new TipoImagen("Responsive_Plan", 1000.0, 1000.0));
        }
        if (tipoImagenService.findOneById(6) == null) {
            tipoImagenService.add(new TipoImagen("Responsive_Plan_Rollover", 1100.0, 1100.0));
        }
    }

    public void addingPlanToTable() {
        if (planService.getPlanById(1) == null) {
            planService.add(new Plan("Básico", "Plan inicial para amateurs", true));
        }
        if (planService.getPlanById(2) == null) {
            planService.add(new Plan("Pro", "Plan de entrenamientos más exigentes", true));
        }
    }

    public void addingToTipoDescuentoTable() {
        if (tipoDescuentoRepository.findOne(1) == null) {
            tipoDescuentoRepository.save(new TipoDescuento("Porcentaje"));
        }
        if (tipoDescuentoRepository.findOne(2) == null) {
            tipoDescuentoRepository.save(new TipoDescuento("Monto Específico"));
        }
    }

    public void addingTipoAudioToTable() {
        if (tipoAudioService.findOne(1) == null) {
            tipoAudioService.save(new TipoAudio("Fuerza", "Fuerza", true));
        }
        if (tipoAudioService.findOne(2) == null) {
            tipoAudioService.save(new TipoAudio("Ejercicios con Pesas", "Ejercicios con Pesas", true));
        }
        if (tipoAudioService.findOne(3) == null) {
            tipoAudioService.save(new TipoAudio("Abdominales", "Abdominales", true));
        }
        if (tipoAudioService.findOne(4) == null) {
            tipoAudioService.save(new TipoAudio("Velocidad", "Velocidad", true));
        }
        if (tipoAudioService.findOne(5) == null) {
            tipoAudioService.save(new TipoAudio("Técnica", "Técnica", true));
        }
        if (tipoAudioService.findOne(6) == null) {
            tipoAudioService.save(new TipoAudio("Resistencia Especial", "Resistencia Especial", true));
        }
        if (tipoAudioService.findOne(7) == null) {
            tipoAudioService.save(new TipoAudio("Estiramientos", "Estiramientos", true));
        }
        if (tipoAudioService.findOne(8) == null) {
            tipoAudioService.save(new TipoAudio("Coordinativos", "Coordinativos", true));
        }
        if (tipoAudioService.findOne(9) == null) {
            tipoAudioService.save(new TipoAudio("Propiocepción", "Propiocepción", true));
        }
        if (tipoAudioService.findOne(10) == null) {
            tipoAudioService.save(new TipoAudio("Calentamiento", "Calentamiento", true));
        }
    }

    public void addingMusculoToTable() {

        if (musculoService.findOne(1) == null) {
            musculoService.save(new Musculo("Pantorrillas"));
        }
        if (musculoService.findOne(2) == null) {
            musculoService.save(new Musculo("Sóleos"));
        }
        if (musculoService.findOne(3) == null) {
            musculoService.save(new Musculo("Tibial anterior"));
        }
        if (musculoService.findOne(4) == null) {
            musculoService.save(new Musculo("Cuádriceps"));
        }
        if (musculoService.findOne(5) == null) {
            musculoService.save(new Musculo("Glúteos"));
        }
        if (musculoService.findOne(6) == null) {
            musculoService.save(new Musculo("Isquiotibiales"));
        }
        if (musculoService.findOne(7) == null) {
            musculoService.save(new Musculo("Espalda baja"));
        }
        if (musculoService.findOne(8) == null) {
            musculoService.save(new Musculo("Hombros"));
        }
        if (musculoService.findOne(9) == null) {
            musculoService.save(new Musculo("Trapecios"));
        }
        if (musculoService.findOne(10) == null) {
            musculoService.save(new Musculo("Bíceps braqueal"));
        }
    }

    public void addingCondicionMejoraToTable(){

        if (capacidadMejoraService.findOne(1) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Fuerza"));
        }
        if (capacidadMejoraService.findOne(2) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Velocidad"));
        }
        if (capacidadMejoraService.findOne(3) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Resistencia"));
        }
        if (capacidadMejoraService.findOne(4) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Flexibilidad"));
        }
        if (capacidadMejoraService.findOne(5) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Métodos respiratorios"));
        }
        if (capacidadMejoraService.findOne(6) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Disciplina"));
        }
        if (capacidadMejoraService.findOne(7) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Hábitos alimenticios"));
        }
        if (capacidadMejoraService.findOne(8) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Cantidad y calidad de sueño"));
        }
        if (capacidadMejoraService.findOne(9) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Administración del esfuerzo"));
        }
        if (capacidadMejoraService.findOne(10) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Proceso de recuperación"));
        }
        if (capacidadMejoraService.findOne(11) == null) {
            capacidadMejoraService.save(new CapacidadMejora("Proceso de recuperación"));
        }
    }

    public void addingAudiosDemo(){
        if(audioService.findOne(1) == null) {
            List<TipoAudio> tiposAudio = tipoAudioService.findAll();
            int i = 0;
            for (TipoAudio ta : tiposAudio) {
                audioService.save(new Audio("4 X  rept x 25'' de R " + (++i), "4 X  rept x 25'' de R " + (i), "12.30KB", "00:00:01", true, UUID.fromString("3ac011a8-90b7-46d2-9b87-795899174b79"), "/1/3ac011a8-90b7-46d2-9b87-795899174b79.mp3", "C://WorkoutAppRepository/Audios/1/3ac011a8-90b7-46d2-9b87-795899174b79.mp3", ta.getId()));
                audioService.save(new Audio("4 X  rept x 25'' de R " + (++i), "4 X  rept x 25'' de R " + (i), "12.30KB", "00:00:01", true, UUID.fromString("3ac011a8-90b7-46d2-9b87-795899174b79"), "/1/3ac011a8-90b7-46d2-9b87-795899174b79.mp3", "C://WorkoutAppRepository/Audios/1/3ac011a8-90b7-46d2-9b87-795899174b79.mp3", ta.getId()));
                audioService.save(new Audio("4 X  rept x 25'' de R " + (++i), "4 X  rept x 25'' de R " + (i), "12.30KB", "00:00:01", true, UUID.fromString("3ac011a8-90b7-46d2-9b87-795899174b79"), "/1/3ac011a8-90b7-46d2-9b87-795899174b79.mp3", "C://WorkoutAppRepository/Audios/1/3ac011a8-90b7-46d2-9b87-795899174b79.mp3", ta.getId()));
                audioService.save(new Audio("4 X  rept x 25'' de R " + (++i), "4 X  rept x 25'' de R " + (i), "12.30KB", "00:00:01", true, UUID.fromString("3ac011a8-90b7-46d2-9b87-795899174b79"), "/1/3ac011a8-90b7-46d2-9b87-795899174b79.mp3", "C://WorkoutAppRepository/Audios/1/3ac011a8-90b7-46d2-9b87-795899174b79.mp3", ta.getId()));
            }
        }
    }


    public void addingProfilesToTable() {
        if (tipoUsuarioService.findOneById(1) == null) {
            tipoUsuarioService.add(new TipoUsuario("Administrador"));
        }
        if (tipoUsuarioService.findOneById(2) == null) {
            tipoUsuarioService.add(new TipoUsuario("Entrenador"));
        }
        if (tipoUsuarioService.findOneById(3) == null) {
            tipoUsuarioService.add(new TipoUsuario("Cliente"));
        }

    }

    public void addingRolToTable(){
        if(rolService.findOne(1) == null) rolService.save(new Rol("Administrador","ROLE_ADMIN"));
        if(rolService.findOne(2) == null) rolService.save(new Rol("Entrenador","ROLE_TRAINER"));
        if(rolService.findOne(3) == null) rolService.save(new Rol("Cliente Runner","ROLE_RUNNER"));
        if(rolService.findOne(4) == null) rolService.save(new Rol("Cliente Tienda","ROLE_STORE"));
    }

    public void addingTipoDocumentoToTable(){
        if(tipoDocumentoService.findOne(1) == null) tipoDocumentoService.save(new TipoDocumento("DNI"));
        if(tipoDocumentoService.findOne(2) == null) tipoDocumentoService.save(new TipoDocumento("CE"));
    }

    public void addingKilometrajBase(){
        if(kilometrajeBaseService.findOne(1) == null) kilometrajeBaseService.save(new KilometrajeBase(10,1,1,30.67));
        if(kilometrajeBaseService.findOne(2) == null) kilometrajeBaseService.save(new KilometrajeBase(10,1,2,41.14));
        if(kilometrajeBaseService.findOne(3) == null) kilometrajeBaseService.save(new KilometrajeBase(10,1,3,34.41));
        if(kilometrajeBaseService.findOne(4) == null) kilometrajeBaseService.save(new KilometrajeBase(10,1,4,11.22));

        if(kilometrajeBaseService.findOne(5) == null) kilometrajeBaseService.save(new KilometrajeBase(21,1,1,38.15));
        if(kilometrajeBaseService.findOne(6) == null) kilometrajeBaseService.save(new KilometrajeBase(21,1,2,52.36));
        if(kilometrajeBaseService.findOne(7) == null) kilometrajeBaseService.save(new KilometrajeBase(21,1,3,40.39));
        if(kilometrajeBaseService.findOne(8) == null) kilometrajeBaseService.save(new KilometrajeBase(21,1,4,22.44));

        if(kilometrajeBaseService.findOne(9) == null) kilometrajeBaseService.save(new KilometrajeBase(42,1,1,59.84));
        if(kilometrajeBaseService.findOne(10) == null) kilometrajeBaseService.save(new KilometrajeBase(42,1,2,101.00));
        if(kilometrajeBaseService.findOne(11) == null) kilometrajeBaseService.save(new KilometrajeBase(42,1,3,74.80));
        if(kilometrajeBaseService.findOne(12) == null) kilometrajeBaseService.save(new KilometrajeBase(42,1,4,29.92));

        if(kilometrajeBaseService.findOne(13) == null) kilometrajeBaseService.save(new KilometrajeBase(10,2,1,34.85));
        if(kilometrajeBaseService.findOne(14) == null) kilometrajeBaseService.save(new KilometrajeBase(10,2,2,46.75));
        if(kilometrajeBaseService.findOne(15) == null) kilometrajeBaseService.save(new KilometrajeBase(10,2,3,39.10));
        if(kilometrajeBaseService.findOne(16) == null) kilometrajeBaseService.save(new KilometrajeBase(10,2,4,12.75));

        if(kilometrajeBaseService.findOne(17) == null) kilometrajeBaseService.save(new KilometrajeBase(21,2,1,43.35));
        if(kilometrajeBaseService.findOne(18) == null) kilometrajeBaseService.save(new KilometrajeBase(21,2,2,59.50));
        if(kilometrajeBaseService.findOne(19) == null) kilometrajeBaseService.save(new KilometrajeBase(21,2,3,45.90));
        if(kilometrajeBaseService.findOne(20) == null) kilometrajeBaseService.save(new KilometrajeBase(21,2,4,25.50));

        if(kilometrajeBaseService.findOne(21) == null) kilometrajeBaseService.save(new KilometrajeBase(42,2,1,68.00));
        if(kilometrajeBaseService.findOne(22) == null) kilometrajeBaseService.save(new KilometrajeBase(42,2,2,114.80));
        if(kilometrajeBaseService.findOne(23) == null) kilometrajeBaseService.save(new KilometrajeBase(42,2,3,85.00));
        if(kilometrajeBaseService.findOne(24) == null) kilometrajeBaseService.save(new KilometrajeBase(42,2,4,34.00));

        if(kilometrajeBaseService.findOne(25) == null) kilometrajeBaseService.save(new KilometrajeBase(10,3,1,41.00));
        if(kilometrajeBaseService.findOne(26) == null) kilometrajeBaseService.save(new KilometrajeBase(10,3,2,55.00));
        if(kilometrajeBaseService.findOne(27) == null) kilometrajeBaseService.save(new KilometrajeBase(10,3,3,46.00));
        if(kilometrajeBaseService.findOne(28) == null) kilometrajeBaseService.save(new KilometrajeBase(10,3,4,15.00));

        if(kilometrajeBaseService.findOne(29) == null) kilometrajeBaseService.save(new KilometrajeBase(21,3,1,51.00));
        if(kilometrajeBaseService.findOne(30) == null) kilometrajeBaseService.save(new KilometrajeBase(21,3,2,70.00));
        if(kilometrajeBaseService.findOne(31) == null) kilometrajeBaseService.save(new KilometrajeBase(21,3,3,54.00));
        if(kilometrajeBaseService.findOne(32) == null) kilometrajeBaseService.save(new KilometrajeBase(21,3,4,30.00));

        if(kilometrajeBaseService.findOne(33) == null) kilometrajeBaseService.save(new KilometrajeBase(42,3,1,80.00));
        if(kilometrajeBaseService.findOne(34) == null) kilometrajeBaseService.save(new KilometrajeBase(42,3,2,135.00));
        if(kilometrajeBaseService.findOne(35) == null) kilometrajeBaseService.save(new KilometrajeBase(42,3,3,100.00));
        if(kilometrajeBaseService.findOne(36) == null) kilometrajeBaseService.save(new KilometrajeBase(42,3,4,40.00));

    }

    public void addingApplicationParameters() {
        if (parametroService.findByClave("MAIN_ROUTE") == null) {
            parametroService.add(new Parametro("MAIN_ROUTE", mainRoute));
        }
    }

    public void addingToContextSession() {
        context.setAttribute("MAIN_ROUTE", parametroService.findByClave("MAIN_ROUTE").getValor());
        context.setAttribute("version", currentVersion);
    }

    public void addingInitUsers() {
        SecurityUser securityUser = userRepository.findByUsername("admin");
        if (securityUser == null) {
            SecurityUser secUser = new SecurityUser();
            secUser.setUsername("admin");
            secUser.setPassword(new BCryptPasswordEncoder().encode("@dmin@2018"));
            secUser.setEnabled(true);

            //Roles
            SecurityRole sr = new SecurityRole();
            sr.setRole("ROLE_ADMIN");

            //Privileges
            SecurityPrivilege sp = new SecurityPrivilege();
            sp.setPrivilege("READ_PRIVILEGE");
            sp.setSecurityRole(sr);
            SecurityPrivilege sp1 = new SecurityPrivilege();
            sp1.setPrivilege("WRITE_PRIVILEGE");
            sp1.setSecurityRole(sr);
            //Set Privileges
            Set<SecurityPrivilege> listSp = new HashSet<>();
            listSp.add(sp);
            listSp.add(sp1);
            //Adding to Role father
            sr.setPrivileges(listSp);
            //Set Roles(Only 1)
            Set<SecurityRole> listSr = new HashSet<>();
            listSr.add(sr);
            //Adding to User
            secUser.setRoles(listSr);
            userRepository.save(secUser);

            //Agregando el primer entrenador
            SecurityUser secUserTrainer = new SecurityUser();
            secUserTrainer.setUsername("info@runfit.pe");
            secUserTrainer.setPassword(new BCryptPasswordEncoder().encode("runfit"));
            secUserTrainer.setEnabled(true);

            //Lista Roles
            Set<SecurityRole> lstRoles = new HashSet<>();
            //Roles
            SecurityRole rol = new SecurityRole();
            rol.setRole("ROLE_ADMIN");
            SecurityRole rol1 = new SecurityRole();
            rol1.setRole("ROLE_TRAINER");
            lstRoles.add(rol);
            lstRoles.add(rol1);
            //Añadiendo roles al secUser
            secUserTrainer.setRoles(lstRoles);
            //Añadiendole los datos detalle del entrenador(TB: Usuario)
            Usuario usuario = new Usuario();
            usuario.setNombres("Petter");
            usuario.setApellidoPaterno("Carranza");
            usuario.setApellidoMaterno("Camino");
            usuario.setMovil("51 987654321");
            usuario.setTelefonoFijo("5532133");
            usuario.setFlagRutinarioCe(true);
            usuario.setCorreo("info@runfit.pe");
            usuario.setNumeroDocumento("44444444");
            usuario.setUsername("info@runfit.pe");
            List<com.itsight.domain.jsonb.Rol> lstR = new ArrayList<>();
            com.itsight.domain.jsonb.Rol r = new com.itsight.domain.jsonb.Rol();
            r.setNombre("ROLE_TRAINER");
            r.setId(2);
            lstR.add(r);
            usuario.setRoles(lstR);
            usuario.setTipoDocumento(1);
            usuario.setTipoUsuario(2);
            usuario.setFlagActivo(true);
            usuario.setCodigoTrainer("info@runfit.pe");
            secUserTrainer.addUsuario(usuario);
            userRepository.save(secUserTrainer);
            usuarioService.cargarRutinarioCe(secUserTrainer.getId());


            //Agregando cliente a entrenador creado
            Usuario usuario1 = new Usuario();
            SecurityUser secUserCliente = new SecurityUser();
            secUserCliente.setUsername("pernio16");
            secUserCliente.setPassword(new BCryptPasswordEncoder().encode("@dmin@2018"));
            secUserCliente.setEnabled(true);

            //Lista Roles
            Set<SecurityRole> lstRolesCli = new HashSet<>();
            //Roles
            SecurityRole rolCli = new SecurityRole();
            rolCli.setRole("ROLE_RUNNER");
            lstRolesCli.add(rolCli);
            //Añadiendo roles al secUser
            secUserCliente.setRoles(lstRolesCli);
            //Añadiendole los datos detalle del entrenador(TB: Usuario)
            usuario1.setNombres("Pernio");
            usuario1.setApellidoPaterno("Rodriguez");
            usuario1.setApellidoMaterno("Alcantará");
            usuario1.setMovil("51 987654321");
            usuario1.setTelefonoFijo("5532133");
            usuario1.setFlagRutinarioCe(true);
            usuario1.setCorreo("pernio16.carranza@itsight.pe");
            usuario1.setNumeroDocumento("44444444");
            usuario1.setUsername("pernio16");
            List<com.itsight.domain.jsonb.Rol> lstRolCli = new ArrayList<>();
            com.itsight.domain.jsonb.Rol rolCliJson = new com.itsight.domain.jsonb.Rol();
            rolCliJson.setNombre("ROLE_RUNNER");
            rolCliJson.setId(2);
            lstRolCli.add(rolCliJson);
            usuario1.setRoles(lstRolCli);
            usuario1.setTipoDocumento(1);
            usuario1.setFechaNacimiento(Parseador.fromStringToDate("1987-01-01"));
            usuario1.setTipoUsuario(3);
            usuario1.setFlagActivo(true);
            secUserCliente.addUsuario(usuario1);
            userRepository.save(secUserCliente);
            //Guardando al cliente en la red del entrenador creado anteriormente
            redFitnessService.save( new RedFitness(usuario.getUsername(), usuario1.getId()));

            //Agregando sus porcentajes base para la creación de macro-ciclos
            if(porcentajesKilometrajeService.findOne(1) == null){
                PorcentajesKilometraje porcentajes;
                Integer[] maratonDistancias = {10, 21, 42};

                for(int i=0; i<3; i++){
                    porcentajes = new PorcentajesKilometraje();
                    porcentajes.setDistancia(maratonDistancias[i]);
                    porcentajes.setTrainer(new Usuario(2));
                    List<PorcKiloTipo> lstPorcKiloTipo = new ArrayList<>();
                    for(int k=1; k<4; k++){
                        PorcKiloTipo porcKiloTipo = new PorcKiloTipo();
                        porcKiloTipo.setTipo(k);
                        List<PorcKiloTipoSema> lstPorcKiloTipoSema = new ArrayList<>();
                        for(int y=4; y<21; y++) {
                            PorcKiloTipoSema porcKiloTipoSema = new PorcKiloTipoSema();
                            porcKiloTipoSema.setTotalSemanas(y);
                            List<Integer> porcents = new ArrayList<>(y);
                            for(int s=0; s<y;s++){
                                porcents.add(70-s);
                            }
                            porcKiloTipoSema.setPorcentajes(porcents);
                            lstPorcKiloTipoSema.add(porcKiloTipoSema);
                        }
                        porcKiloTipo.setSemanas(lstPorcKiloTipoSema);
                        lstPorcKiloTipo.add(porcKiloTipo);
                    }
                    porcentajes.setPorcKiloTipos(lstPorcKiloTipo);
                    porcentajesKilometrajeService.save(porcentajes);
                }
            }

            //Agregando su información fitness básica
            UsuarioFitness usuarioFitness1 = new UsuarioFitness();
            usuarioFitness1.setNivel(3);
            usuarioFitness1.setEstadoCivil(1);
            usuarioFitness1.setImc(15);
            usuarioFitness1.setPeso(BigDecimal.valueOf(66));
            usuarioFitness1.setSexo(1);
            usuarioFitness1.setDesgasteZapatilla("Inicio");
            usuarioFitness1.setObjetivosDescripcion("Demo");
            usuarioFitness1.setTerrenoPredominante("Asfalto");
            usuarioFitness1.setDiaDescanso(1);
            CondicionAnatomica ca = new CondicionAnatomica();
            ca.setFrecuenciaCardiaca(65);
            ca.setFrecuenciaCardiacaMaxima(200);
            usuarioFitness1.setCondicionAnatomica(ca);
            usuarioFitness1.setTiemposDisponibles(new ArrayList<>());
            usuarioFitness1.setObjetivos(new ArrayList<>());
            usuarioFitness1.setKilometrajePromedioSemana(BigDecimal.valueOf(20));
            usuarioFitness1.setMejoras(new ArrayList<>());
            usuarioFitness1.setFrecuenciaComunicacion(1);
            usuarioFitness1.setViaConexion("Demo");
            usuarioFitness1.setTalla(166);
            List<CompetenciaRunner> comps = new ArrayList<>();
            Integer[] tiempos = {10,21,42};
            String[] fechas = {"2018-11-10","2018-12-10","2019-01-27"};
            for(int i=0; i<3;i++){
                CompetenciaRunner cr = new CompetenciaRunner();
                cr.setDistancia(tiempos[i]);
                cr.setFecha(fechas[i]);
                cr.setNombre("Maratón "+i);
                cr.setPrioridad(2);
                cr.setTiempoObjetivo("01:55");
                comps.add(cr);
            }
            comps.get(2).setPrioridad(1);
            usuarioFitness1.setCompetencias(comps);
            usuarioFitness1.setUsuario(3);
            usuarioFitnessService.save(usuarioFitness1);
        } else {
            System.out.println("> Record already exist <");
        }

    }

    public void creatingFileDirectories() {
        String[] childPaths = {
                "/ContratosPaquete",
                "/Planes",
                "/Audios",
                "/Categorias",
                "/CategoriasEjercicio",
                "/GruposVideo",
                "/Productos",
                "/Videos",
                "/Documentos",
                "/Productos/Presentacion",
        };
        Utilitarios.createDirectoryStartUp(context.getAttribute("MAIN_ROUTE").toString(), childPaths);
    }
}
