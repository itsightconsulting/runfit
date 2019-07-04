package com.itsight.component;

import com.itsight.domain.CondicionMejora;
import com.itsight.domain.Musculo;
import com.itsight.domain.Parametro;
import com.itsight.domain.Rol;
import com.itsight.domain.*;
import com.itsight.domain.jsonb.*;
import com.itsight.repository.*;
import com.itsight.service.*;
import com.itsight.util.Parseador;
import com.itsight.util.Utilitarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SecurityUserRepository userRepository;

    @Autowired
    private TrainerService trainerService;

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
    private CondicionMejoraService condicionMejoraService;

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @Autowired
    private RedFitnessService redFitnessService;

    @Autowired
    private KilometrajeBaseService kilometrajeBaseService;

    @Autowired
    private PorcentajesKilometrajeService porcentajesKilometrajeService;

    @Autowired
    private ClienteFitnessService clienteFitnessService;

    @Autowired
    private ConfiguracionGeneralService configuracionGeneralService;

    @Autowired
    private ConfiguracionClienteService configuracionClienteService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PaisService paisService;

    @Autowired
    private UbPeruService ubPeruService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private ModuloService moduloService;

    @Autowired
    private CorreoService correoService;

    @Autowired
    private BancoService bancoService;

    @Autowired
    private TipoTrainerService tipoTrainerService;

    @Value("${main.repository}")
    private String mainRoute;

    private final Long currentVersion = new Date().getTime();

    private static int index = 1;

    @Autowired
    private BagForestRepository bagForestRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private TipoRutinaService tipoRutinaService;

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
            addingConfiguracionGeneralToTable();
            addingKilometrajeBase();
            addingPaises();
            addingDisciplinas();
            addingTipoTrainers();
            addingTipoRutina();
            try {
                addingUbPeru();
                addingModulo();
                addingCorreo();
                addingBanco();
                addingIdioma();
            } catch (IOException ex){
                ex.printStackTrace();
            }
            addingAudiosDemo();

            if(bagForestRepository.findById(1).orElse(null) == null) {
                insertACategoriaEjercicio();
                insertASubCategoriaEjercicio();
                insertAEspecificacionSubCategoriaEjercicio();
            }

            if(bagForestRepository.findById(2).orElse(null) == null) {
                insertAGrupoVideo();
                insertACategoriaVideo();
                insertASubCategoriaVideo();
                try {
                    addingVideo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        if(subCategoriaEjercicioService.findOne(26) == null) subCategoriaEjercicioService.save(new SubCategoriaEjercicio("Integeritud de paso",7, true));
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
        Integer[] flexSubCatIds = new Integer[]{10,11,12,13,14,15,16,17,18,19,20};
        List<Integer> lstFlexSubCatIds  = Arrays.asList(flexSubCatIds);
        for(SubCategoriaEjercicio sce:subCategoriaEjercicioService.findAllByOrderById()){
            if(lstFlexSubCatIds.contains(sce.getId())){
                if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("General", sce.getId(),1, true));
            }else{
                for(int i=1; i<4;i++){
                    if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("Acondicionamiento", sce.getId(),i, true));
                    if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("General",sce.getId(),i, true));
                    if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("Específica",sce.getId(),i, true));
                    if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("Precompetitiva",sce.getId(),i, true));
                    if(especificacionSubCategoriaService.findOne(index++) == null) especificacionSubCategoriaService.save(new EspecificacionSubCategoria("Competitiva",sce.getId(),i, true));
                }
            }
        }
    }

    public void insertAGrupoVideo(){
        categoriaVideoService.insertArtificio();
        if(grupoVideoService.findOne(1) == null) grupoVideoService.save(
                new GrupoVideo("Fuerza tren inferior",2, true, "87e1bccc-5d86-4db0-a59a-0610553868a1.png", "", UUID.fromString("87e1bccc-5d86-4db0-a59a-0610553868a1")));
        if(grupoVideoService.findOne(2) == null) grupoVideoService.save(
                new GrupoVideo("Fuerza tren superior",2, true, "3110f934-4bdf-4b3a-a207-d5334ce31a5d.png", "", UUID.fromString("3110f934-4bdf-4b3a-a207-d5334ce31a5d")));
        if(grupoVideoService.findOne(3) == null) grupoVideoService.save(
                new GrupoVideo("Técnicos",2, true, "ce5a818d-3032-4286-8b25-7bfe0dbde8be.png", "", UUID.fromString("ce5a818d-3032-4286-8b25-7bfe0dbde8be")));
        if(grupoVideoService.findOne(4) == null) grupoVideoService.save(
                new GrupoVideo("Flexibilidad - Movilidad - Masajes",2, true, "e22d1f51-b10d-4e9e-bd6b-fa85eefe8c6f.png", "", UUID.fromString("e22d1f51-b10d-4e9e-bd6b-fa85eefe8c6f")));
        if(grupoVideoService.findOne(5) == null) grupoVideoService.save(
                new GrupoVideo("Propioceptivos",2, true, "741081a8-0156-4c20-8117-91c086188ef7.png", "", UUID.fromString("741081a8-0156-4c20-8117-91c086188ef7")));
    }

    public void insertACategoriaVideo() {
        if(categoriaVideoService.findOne(1) == null) categoriaVideoService.save(new CategoriaVideo("Cuádriceps",1, true));
        if(categoriaVideoService.findOne(2) == null) categoriaVideoService.save(new CategoriaVideo("Glúteos",1, true));
        if(categoriaVideoService.findOne(3) == null) categoriaVideoService.save(new CategoriaVideo("Isquiotibiales",1, true));
        if(categoriaVideoService.findOne(4) == null) categoriaVideoService.save(new CategoriaVideo("Flexóres de la cadera",1, true));
        if(categoriaVideoService.findOne(5) == null) categoriaVideoService.save(new CategoriaVideo("Pliométricos",1, true));
        if(categoriaVideoService.findOne(6) == null) categoriaVideoService.save(new CategoriaVideo("Tríceps sural",1, true));
        if(categoriaVideoService.findOne(7) == null) categoriaVideoService.save(new CategoriaVideo("Tibiales",1, true));
        if(categoriaVideoService.findOne(8) == null) categoriaVideoService.save(new CategoriaVideo("Tobillos",1, true));
        if(categoriaVideoService.findOne(9) == null) categoriaVideoService.save(new CategoriaVideo("Plantares",1, true));
        if(categoriaVideoService.findOne(10) == null) categoriaVideoService.save(new CategoriaVideo("Rotadores",1, true));
        if(categoriaVideoService.findOne(11) == null) categoriaVideoService.save(new CategoriaVideo("Tensor y B. Iliotibial",1, true));
        if(categoriaVideoService.findOne(12) == null) categoriaVideoService.save(new CategoriaVideo("Aductores",1, true));

        if(categoriaVideoService.findOne(13) == null) categoriaVideoService.save(new CategoriaVideo("Cuello",2, true));
        if(categoriaVideoService.findOne(14) == null) categoriaVideoService.save(new CategoriaVideo("Deltoides",2, true));
        if(categoriaVideoService.findOne(15) == null) categoriaVideoService.save(new CategoriaVideo("Espalda",2, true));
        if(categoriaVideoService.findOne(16) == null) categoriaVideoService.save(new CategoriaVideo("Pectorales",2, true));
        if(categoriaVideoService.findOne(17) == null) categoriaVideoService.save(new CategoriaVideo("Flexores del codo",2, true));
        if(categoriaVideoService.findOne(18) == null) categoriaVideoService.save(new CategoriaVideo("Tríceps",2, true));
        if(categoriaVideoService.findOne(19) == null) categoriaVideoService.save(new CategoriaVideo("Core",2, true));
        if(categoriaVideoService.findOne(20) == null) categoriaVideoService.save(new CategoriaVideo("Braceo",2, true));
        if(categoriaVideoService.findOne(21) == null) categoriaVideoService.save(new CategoriaVideo("Globales",2, true));

        if(categoriaVideoService.findOne(22) == null) categoriaVideoService.save(new CategoriaVideo("Zancada",3, true));
        if(categoriaVideoService.findOne(23) == null) categoriaVideoService.save(new CategoriaVideo("Coordinativos", 3, true));
        if(categoriaVideoService.findOne(24) == null) categoriaVideoService.save(new CategoriaVideo("Trotes",3, true));
        if(categoriaVideoService.findOne(25) == null) categoriaVideoService.save(new CategoriaVideo("Potencia",3, true));
        if(categoriaVideoService.findOne(26) == null) categoriaVideoService.save(new CategoriaVideo("Postulares",3, true));
        if(categoriaVideoService.findOne(27) == null) categoriaVideoService.save(new CategoriaVideo("Braceo",3, true));
        if(categoriaVideoService.findOne(28) == null) categoriaVideoService.save(new CategoriaVideo("ABC", 3, true));
        if(categoriaVideoService.findOne(29) == null) categoriaVideoService.save(new CategoriaVideo("Piques", 3, true));

        if(categoriaVideoService.findOne(30) == null) categoriaVideoService.save(new CategoriaVideo("Brazos",4, true));
        if(categoriaVideoService.findOne(31) == null) categoriaVideoService.save(new CategoriaVideo("Cadenas. M.",4, true));
        if(categoriaVideoService.findOne(32) == null) categoriaVideoService.save(new CategoriaVideo("Caderas",4, true));
        if(categoriaVideoService.findOne(33) == null) categoriaVideoService.save(new CategoriaVideo("Cervical",4, true));
        if(categoriaVideoService.findOne(34) == null) categoriaVideoService.save(new CategoriaVideo("Dinámicos",4, true));
        if(categoriaVideoService.findOne(35) == null) categoriaVideoService.save(new CategoriaVideo("Lumbar",4, true));
        if(categoriaVideoService.findOne(36) == null) categoriaVideoService.save(new CategoriaVideo("Masajes",4, true));
        if(categoriaVideoService.findOne(37) == null) categoriaVideoService.save(new CategoriaVideo("Movilidad",4, true));
        if(categoriaVideoService.findOne(38) == null) categoriaVideoService.save(new CategoriaVideo("Rodilla",4, true));
        if(categoriaVideoService.findOne(39) == null) categoriaVideoService.save(new CategoriaVideo("Tobillos",4, true));

        if(categoriaVideoService.findOne(40) == null) categoriaVideoService.save(new CategoriaVideo("En pelotas",5, true));
        if(categoriaVideoService.findOne(41) == null) categoriaVideoService.save(new CategoriaVideo("Miembros superiores",5, true));
        if(categoriaVideoService.findOne(42) == null) categoriaVideoService.save(new CategoriaVideo("Clásicos",5, true));
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
            categoriaService.save(new Categoria(0, "Performance", "Performance", true, "/1/b94d05e5-8101-492d-9105-e32c75cf0ab0.png", "C://WorkoutAppRepository/Categorias/1/b94d05e5-8101-492d-9105-e32c75cf0ab0.png", UUID.fromString("b94d05e5-8101-492d-9105-e32c75cf0ab0")));
        }
        if (categoriaService.findOne(2) == null) {
            categoriaService.save(new Categoria(0, "Técnica", "Técnica", true, "/2/5830a767-611f-4a3e-a2c5-34b5831afa88.jpg", "C://WorkoutAppRepository/Categorias/2/5830a767-611f-4a3e-a2c5-34b5831afa88.jpg", UUID.fromString("5830a767-611f-4a3e-a2c5-34b5831afa88")));
        }
        if (categoriaService.findOne(3) == null) {
            categoriaService.save(new Categoria(0, "Fortalecimiento", "Fortalecimiento", true, "/3/61a3a05e-ec3c-48f0-b978-92ab3d1155f3.jpg", "C://WorkoutAppRepository/Categorias/3/61a3a05e-ec3c-48f0-b978-92ab3d1155f3.jpg", UUID.fromString("61a3a05e-ec3c-48f0-b978-92ab3d1155f3")));
        }
        if (categoriaService.findOne(4) == null) {
            categoriaService.save(new Categoria(0, "Metodología", "Metodología", true, "/4/977fe861-828e-40a5-a4cf-509b1adb5fcb.jpg", "C://WorkoutAppRepository/Categorias/4/977fe861-828e-40a5-a4cf-509b1adb5fcb.jpg", UUID.fromString("977fe861-828e-40a5-a4cf-509b1adb5fcb")));
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
        if (tipoDescuentoRepository.findById(1).orElse(null) == null) {
            tipoDescuentoRepository.save(new TipoDescuento("Porcentaje"));
        }
        if (tipoDescuentoRepository.findById(2).orElse(null) == null) {
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

        if (condicionMejoraService.findOne(1) == null) {
            condicionMejoraService.save(new CondicionMejora("Fuerza"));
        }
        if (condicionMejoraService.findOne(2) == null) {
            condicionMejoraService.save(new CondicionMejora("Velocidad"));
        }
        if (condicionMejoraService.findOne(3) == null) {
            condicionMejoraService.save(new CondicionMejora("Resistencia"));
        }
        if (condicionMejoraService.findOne(4) == null) {
            condicionMejoraService.save(new CondicionMejora("Flexibilidad"));
        }
        if (condicionMejoraService.findOne(5) == null) {
            condicionMejoraService.save(new CondicionMejora("Métodos respiratorios"));
        }
        if (condicionMejoraService.findOne(6) == null) {
            condicionMejoraService.save(new CondicionMejora("Proceso de recuperación"));
        }
        if (condicionMejoraService.findOne(7) == null) {
            condicionMejoraService.save(new CondicionMejora("Técnica de carrera"));
        }
        if (condicionMejoraService.findOne(8) == null) {
            condicionMejoraService.save(new CondicionMejora("Disciplina"));
        }
        if (condicionMejoraService.findOne(9) == null) {
            condicionMejoraService.save(new CondicionMejora("Hábitos alimenticios"));
        }
        if (condicionMejoraService.findOne(10) == null) {
            condicionMejoraService.save(new CondicionMejora("Cantidad y calidad de sueño"));
        }
        if (condicionMejoraService.findOne(11) == null) {
            condicionMejoraService.save(new CondicionMejora("Administración del esfuerzo"));
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
        if(rolService.findOne(5) == null) rolService.save(new Rol("Visitante","ROLE_GUEST"));


    }

    public void addingTipoDocumentoToTable(){
        if(tipoDocumentoService.findOne(1) == null) tipoDocumentoService.save(new TipoDocumento("DNI"));
        if(tipoDocumentoService.findOne(2) == null) tipoDocumentoService.save(new TipoDocumento("CE"));
        if(tipoDocumentoService.findOne(3) == null) tipoDocumentoService.save(new TipoDocumento("RUC"));
    }

    public void addingConfiguracionGeneralToTable(){
        if(configuracionGeneralService.findOne(2) == null) configuracionGeneralService.save(new ConfiguracionGeneral("CONTROL_REP_VIDEO", "REPETICION",true, 3));
        if(configuracionGeneralService.findOne(3) == null) configuracionGeneralService.save(new ConfiguracionGeneral("FAV_RUTINA_ID", "", true, 3));
        if(configuracionGeneralService.findOne(4) == null) configuracionGeneralService.save(new ConfiguracionGeneral("FAV_TRAINER_ID", "",true, 3));
        if(configuracionGeneralService.findOne(5) == null) configuracionGeneralService.save(new ConfiguracionGeneral("FAVS_POST_TRAINER", "",true, 3));
        if(configuracionGeneralService.findOne(5) == null) configuracionGeneralService.save(new ConfiguracionGeneral("CONTROL_ENTRENAMIENTO", "DIARIA",true, 3));
    }

    public void addingKilometrajeBase(){
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

    public void addingPaises(){
        if(paisService.findOne(4) == null) paisService.save(new Pais(4, "Afganistán", "AF", "AFG"));
        if(paisService.findOne(248) == null) paisService.save(new Pais(248, "Åland", "AX", "ALA"));
        if(paisService.findOne(8) == null) paisService.save(new Pais(8, "Albania", "AL", "ALB"));
        if(paisService.findOne(276) == null) paisService.save(new Pais(276, "Alemania", "DE", "DEU"));
        if(paisService.findOne(20) == null) paisService.save(new Pais(20, "Andorra", "AD", "AND"));
        if(paisService.findOne(24) == null) paisService.save(new Pais(24, "Angola", "AO", "AGO"));
        if(paisService.findOne(660) == null) paisService.save(new Pais(660, "Anguila", "AI", "AIA"));
        if(paisService.findOne(10) == null) paisService.save(new Pais(10, "Antártida", "AQ", "ATA"));
        if(paisService.findOne(28) == null) paisService.save(new Pais(28, "Antigua y Barbuda", "AG", "ATG"));
        if(paisService.findOne(682) == null) paisService.save(new Pais(682, "Arabia Saudita", "SA", "SAU"));
        if(paisService.findOne(12) == null) paisService.save(new Pais(12, "Argelia", "DZ", "DZA"));
        if(paisService.findOne(32) == null) paisService.save(new Pais(32, "Argentina", "AR", "ARG"));
        if(paisService.findOne(51) == null) paisService.save(new Pais(51, "Armenia", "AM", "ARM"));
        if(paisService.findOne(533) == null) paisService.save(new Pais(533, "Aruba", "AW", "ABW"));
        if(paisService.findOne(36) == null) paisService.save(new Pais(36, "Australia", "AU", "AUS"));
        if(paisService.findOne(40) == null) paisService.save(new Pais(40, "Austria", "AT", "AUT"));
        if(paisService.findOne(31) == null) paisService.save(new Pais(31, "Azerbaiyán", "AZ", "AZE"));
        if(paisService.findOne(44) == null) paisService.save(new Pais(44, "Bahamas", "BS", "BHS"));
        if(paisService.findOne(50) == null) paisService.save(new Pais(50, "Bangladés", "BD", "BGD"));
        if(paisService.findOne(52) == null) paisService.save(new Pais(52, "Barbados", "BB", "BRB"));
        if(paisService.findOne(48) == null) paisService.save(new Pais(48, "Baréin", "BH", "BHR"));
        if(paisService.findOne(56) == null) paisService.save(new Pais(56, "Bélgica", "BE", "BEL"));
        if(paisService.findOne(84) == null) paisService.save(new Pais(84, "Belice", "BZ", "BLZ"));
        if(paisService.findOne(204) == null) paisService.save(new Pais(204, "Benín", "BJ", "BEN"));
        if(paisService.findOne(60) == null) paisService.save(new Pais(60, "Bermudas", "BM", "BMU"));
        if(paisService.findOne(112) == null) paisService.save(new Pais(112, "Bielorrusia", "BY", "BLR"));
        if(paisService.findOne(68) == null) paisService.save(new Pais(68, "Bolivia", "BO", "BOL"));
        if(paisService.findOne(535) == null) paisService.save(new Pais(535, "Bonaire, San Eustaquio y Saba", "BQ", "BES"));
        if(paisService.findOne(70) == null) paisService.save(new Pais(70, "Bosnia y Herzegovina", "BA", "BIH"));
        if(paisService.findOne(72) == null) paisService.save(new Pais(72, "Botsuana", "BW", "BWA"));
        if(paisService.findOne(76) == null) paisService.save(new Pais(76, "Brasil", "BR", "BRA"));
        if(paisService.findOne(96) == null) paisService.save(new Pais(96, "Brunéi", "BN", "BRN"));
        if(paisService.findOne(100) == null) paisService.save(new Pais(100, "Bulgaria", "BG", "BGR"));
        if(paisService.findOne(854) == null) paisService.save(new Pais(854, "Burkina Faso", "BF", "BFA"));
        if(paisService.findOne(108) == null) paisService.save(new Pais(108, "Burundi", "BI", "BDI"));
        if(paisService.findOne(64) == null) paisService.save(new Pais(64, "Bután", "BT", "BTN"));
        if(paisService.findOne(132) == null) paisService.save(new Pais(132, "Cabo Verde", "CV", "CPV"));
        if(paisService.findOne(116) == null) paisService.save(new Pais(116, "Camboya", "KH", "KHM"));
        if(paisService.findOne(120) == null) paisService.save(new Pais(120, "Camerún", "CM", "CMR"));
        if(paisService.findOne(124) == null) paisService.save(new Pais(124, "Canadá", "CA", "CAN"));
        if(paisService.findOne(634) == null) paisService.save(new Pais(634, "Catar", "QA", "QAT"));
        if(paisService.findOne(148) == null) paisService.save(new Pais(148, "Chad", "TD", "TCD"));
        if(paisService.findOne(152) == null) paisService.save(new Pais(152, "Chile", "CL", "CHL"));
        if(paisService.findOne(156) == null) paisService.save(new Pais(156, "China", "CN", "CHN"));
        if(paisService.findOne(196) == null) paisService.save(new Pais(196, "Chipre", "CY", "CYP"));
        if(paisService.findOne(170) == null) paisService.save(new Pais(170, "Colombia", "CO", "COL"));
        if(paisService.findOne(174) == null) paisService.save(new Pais(174, "Comoras", "KM", "COM"));
        if(paisService.findOne(408) == null) paisService.save(new Pais(408, "Corea del Norte", "KP", "PRK"));
        if(paisService.findOne(410) == null) paisService.save(new Pais(410, "Corea del Sur", "KR", "KOR"));
        if(paisService.findOne(384) == null) paisService.save(new Pais(384, "Costa de Marfil", "CI", "CIV"));
        if(paisService.findOne(188) == null) paisService.save(new Pais(188, "Costa Rica", "CR", "CRI"));
        if(paisService.findOne(191) == null) paisService.save(new Pais(191, "Croacia", "HR", "HRV"));
        if(paisService.findOne(192) == null) paisService.save(new Pais(192, "Cuba", "CU", "CUB"));
        if(paisService.findOne(531) == null) paisService.save(new Pais(531, "Curazao", "CW", "CUW"));
        if(paisService.findOne(208) == null) paisService.save(new Pais(208, "Dinamarca", "DK", "DNK"));
        if(paisService.findOne(212) == null) paisService.save(new Pais(212, "Dominica", "DM", "DMA"));
        if(paisService.findOne(218) == null) paisService.save(new Pais(218, "Ecuador", "EC", "ECU"));
        if(paisService.findOne(818) == null) paisService.save(new Pais(818, "Egipto", "EG", "EGY"));
        if(paisService.findOne(222) == null) paisService.save(new Pais(222, "El Salvador", "SV", "SLV"));
        if(paisService.findOne(784) == null) paisService.save(new Pais(784, "Emiratos Árabes Unidos", "AE", "ARE"));
        if(paisService.findOne(232) == null) paisService.save(new Pais(232, "Eritrea", "ER", "ERI"));
        if(paisService.findOne(703) == null) paisService.save(new Pais(703, "Eslovaquia", "SK", "SVK"));
        if(paisService.findOne(705) == null) paisService.save(new Pais(705, "Eslovenia", "SI", "SVN"));
        if(paisService.findOne(724) == null) paisService.save(new Pais(724, "España", "ES", "ESP"));
        if(paisService.findOne(840) == null) paisService.save(new Pais(840, "Estados Unidos", "US", "USA"));
        if(paisService.findOne(233) == null) paisService.save(new Pais(233, "Estonia", "EE", "EST"));
        if(paisService.findOne(231) == null) paisService.save(new Pais(231, "Etiopía", "ET", "ETH"));
        if(paisService.findOne(608) == null) paisService.save(new Pais(608, "Filipinas", "PH", "PHL"));
        if(paisService.findOne(246) == null) paisService.save(new Pais(246, "Finlandia", "FI", "FIN"));
        if(paisService.findOne(242) == null) paisService.save(new Pais(242, "Fiyi", "FJ", "FJI"));
        if(paisService.findOne(250) == null) paisService.save(new Pais(250, "Francia", "FR", "FRA"));
        if(paisService.findOne(266) == null) paisService.save(new Pais(266, "Gabón", "GA", "GAB"));
        if(paisService.findOne(270) == null) paisService.save(new Pais(270, "Gambia", "GM", "GMB"));
        if(paisService.findOne(268) == null) paisService.save(new Pais(268, "Georgia", "GE", "GEO"));
        if(paisService.findOne(288) == null) paisService.save(new Pais(288, "Ghana", "GH", "GHA"));
        if(paisService.findOne(292) == null) paisService.save(new Pais(292, "Gibraltar", "GI", "GIB"));
        if(paisService.findOne(308) == null) paisService.save(new Pais(308, "Granada", "GD", "GRD"));
        if(paisService.findOne(300) == null) paisService.save(new Pais(300, "Grecia", "GR", "GRC"));
        if(paisService.findOne(304) == null) paisService.save(new Pais(304, "Groenlandia", "GL", "GRL"));
        if(paisService.findOne(312) == null) paisService.save(new Pais(312, "Guadalupe", "GP", "GLP"));
        if(paisService.findOne(316) == null) paisService.save(new Pais(316, "Guam", "GU", "GUM"));
        if(paisService.findOne(320) == null) paisService.save(new Pais(320, "Guatemala", "GT", "GTM"));
        if(paisService.findOne(254) == null) paisService.save(new Pais(254, "Guayana Francesa", "GF", "GUF"));
        if(paisService.findOne(831) == null) paisService.save(new Pais(831, "Guernsey", "GG", "GGY"));
        if(paisService.findOne(324) == null) paisService.save(new Pais(324, "Guinea", "GN", "GIN"));
        if(paisService.findOne(624) == null) paisService.save(new Pais(624, "Guinea-Bisáu", "GW", "GNB"));
        if(paisService.findOne(226) == null) paisService.save(new Pais(226, "Guinea Ecuatorial", "GQ", "GNQ"));
        if(paisService.findOne(328) == null) paisService.save(new Pais(328, "Guyana", "GY", "GUY"));
        if(paisService.findOne(332) == null) paisService.save(new Pais(332, "Haití", "HT", "HTI"));
        if(paisService.findOne(340) == null) paisService.save(new Pais(340, "Honduras", "HN", "HND"));
        if(paisService.findOne(344) == null) paisService.save(new Pais(344, "Hong Kong", "HK", "HKG"));
        if(paisService.findOne(348) == null) paisService.save(new Pais(348, "Hungría", "HU", "HUN"));
        if(paisService.findOne(356) == null) paisService.save(new Pais(356, "India", "IN", "IND"));
        if(paisService.findOne(360) == null) paisService.save(new Pais(360, "Indonesia", "ID", "IDN"));
        if(paisService.findOne(368) == null) paisService.save(new Pais(368, "Irak", "IQ", "IRQ"));
        if(paisService.findOne(364) == null) paisService.save(new Pais(364, "Irán", "IR", "IRN"));
        if(paisService.findOne(372) == null) paisService.save(new Pais(372, "Irlanda", "IE", "IRL"));
        if(paisService.findOne(74) == null) paisService.save(new Pais(74, "Isla Bouvet", "BV", "BVT"));
        if(paisService.findOne(833) == null) paisService.save(new Pais(833, "Isla de Man", "IM", "IMN"));
        if(paisService.findOne(162) == null) paisService.save(new Pais(162, "Isla de Navidad", "CX", "CXR"));
        if(paisService.findOne(352) == null) paisService.save(new Pais(352, "Islandia", "IS", "ISL"));
        if(paisService.findOne(136) == null) paisService.save(new Pais(136, "Islas Caimán", "KY", "CYM"));
        if(paisService.findOne(166) == null) paisService.save(new Pais(166, "Islas Cocos", "CC", "CCK"));
        if(paisService.findOne(184) == null) paisService.save(new Pais(184, "Islas Cook", "CK", "COK"));
        if(paisService.findOne(234) == null) paisService.save(new Pais(234, "Islas Feroe", "FO", "FRO"));
        if(paisService.findOne(239) == null) paisService.save(new Pais(239, "Islas Georgias del Sur y Sandwich del Sur", "GS", "SGS"));
        if(paisService.findOne(334) == null) paisService.save(new Pais(334, "Islas Heard y McDonald", "HM", "HMD"));
        if(paisService.findOne(238) == null) paisService.save(new Pais(238, "Islas Malvinas", "FK", "FLK"));
        if(paisService.findOne(580) == null) paisService.save(new Pais(580, "Islas Marianas del Norte", "MP", "MNP"));
        if(paisService.findOne(584) == null) paisService.save(new Pais(584, "Islas Marshall", "MH", "MHL"));
        if(paisService.findOne(612) == null) paisService.save(new Pais(612, "Islas Pitcairn", "PN", "PCN"));
        if(paisService.findOne(90) == null) paisService.save(new Pais(90, "Islas Salomón", "SB", "SLB"));
        if(paisService.findOne(796) == null) paisService.save(new Pais(796, "Islas Turcas y Caicos", "TC", "TCA"));
        if(paisService.findOne(581) == null) paisService.save(new Pais(581, "Islas ultramarinas de Estados Unidos", "UM", "UMI"));
        if(paisService.findOne(92) == null) paisService.save(new Pais(92, "Islas Vírgenes Británicas", "VG", "VGB"));
        if(paisService.findOne(850) == null) paisService.save(new Pais(850, "Islas Vírgenes de los Estados Unidos", "VI", "VIR"));
        if(paisService.findOne(376) == null) paisService.save(new Pais(376, "Israel", "IL", "ISR"));
        if(paisService.findOne(380) == null) paisService.save(new Pais(380, "Italia", "IT", "ITA"));
        if(paisService.findOne(388) == null) paisService.save(new Pais(388, "Jamaica", "JM", "JAM"));
        if(paisService.findOne(392) == null) paisService.save(new Pais(392, "Japón", "JP", "JPN"));
        if(paisService.findOne(832) == null) paisService.save(new Pais(832, "Jersey", "JE", "JEY"));
        if(paisService.findOne(400) == null) paisService.save(new Pais(400, "Jordania", "JO", "JOR"));
        if(paisService.findOne(398) == null) paisService.save(new Pais(398, "Kazajistán", "KZ", "KAZ"));
        if(paisService.findOne(404) == null) paisService.save(new Pais(404, "Kenia", "KE", "KEN"));
        if(paisService.findOne(417) == null) paisService.save(new Pais(417, "Kirguistán", "KG", "KGZ"));
        if(paisService.findOne(296) == null) paisService.save(new Pais(296, "Kiribati", "KI", "KIR"));
        if(paisService.findOne(414) == null) paisService.save(new Pais(414, "Kuwait", "KW", "KWT"));
        if(paisService.findOne(418) == null) paisService.save(new Pais(418, "Laos", "LA", "LAO"));
        if(paisService.findOne(426) == null) paisService.save(new Pais(426, "Lesoto", "LS", "LSO"));
        if(paisService.findOne(428) == null) paisService.save(new Pais(428, "Letonia", "LV", "LVA"));
        if(paisService.findOne(422) == null) paisService.save(new Pais(422, "Líbano", "LB", "LBN"));
        if(paisService.findOne(430) == null) paisService.save(new Pais(430, "Liberia", "LR", "LBR"));
        if(paisService.findOne(434) == null) paisService.save(new Pais(434, "Libia", "LY", "LBY"));
        if(paisService.findOne(438) == null) paisService.save(new Pais(438, "Liechtenstein", "LI", "LIE"));
        if(paisService.findOne(440) == null) paisService.save(new Pais(440, "Lituania", "LT", "LTU"));
        if(paisService.findOne(442) == null) paisService.save(new Pais(442, "Luxemburgo", "LU", "LUX"));
        if(paisService.findOne(446) == null) paisService.save(new Pais(446, "Macao", "MO", "MAC"));
        if(paisService.findOne(807) == null) paisService.save(new Pais(807, "Macedonia", "MK", "MKD"));
        if(paisService.findOne(450) == null) paisService.save(new Pais(450, "Madagascar", "MG", "MDG"));
        if(paisService.findOne(458) == null) paisService.save(new Pais(458, "Malasia", "MY", "MYS"));
        if(paisService.findOne(454) == null) paisService.save(new Pais(454, "Malaui", "MW", "MWI"));
        if(paisService.findOne(462) == null) paisService.save(new Pais(462, "Maldivas", "MV", "MDV"));
        if(paisService.findOne(466) == null) paisService.save(new Pais(466, "Malí", "ML", "MLI"));
        if(paisService.findOne(470) == null) paisService.save(new Pais(470, "Malta", "MT", "MLT"));
        if(paisService.findOne(504) == null) paisService.save(new Pais(504, "Marruecos", "MA", "MAR"));
        if(paisService.findOne(474) == null) paisService.save(new Pais(474, "Martinica", "MQ", "MTQ"));
        if(paisService.findOne(480) == null) paisService.save(new Pais(480, "Mauricio", "MU", "MUS"));
        if(paisService.findOne(478) == null) paisService.save(new Pais(478, "Mauritania", "MR", "MRT"));
        if(paisService.findOne(175) == null) paisService.save(new Pais(175, "Mayotte", "YT", "MYT"));
        if(paisService.findOne(484) == null) paisService.save(new Pais(484, "México", "MX", "MEX"));
        if(paisService.findOne(583) == null) paisService.save(new Pais(583, "Micronesia", "FM", "FSM"));
        if(paisService.findOne(498) == null) paisService.save(new Pais(498, "Moldavia", "MD", "MDA"));
        if(paisService.findOne(492) == null) paisService.save(new Pais(492, "Mónaco", "MC", "MCO"));
        if(paisService.findOne(496) == null) paisService.save(new Pais(496, "Mongolia", "MN", "MNG"));
        if(paisService.findOne(499) == null) paisService.save(new Pais(499, "Montenegro", "ME", "MNE"));
        if(paisService.findOne(500) == null) paisService.save(new Pais(500, "Montserrat", "MS", "MSR"));
        if(paisService.findOne(508) == null) paisService.save(new Pais(508, "Mozambique", "MZ", "MOZ"));
        if(paisService.findOne(104) == null) paisService.save(new Pais(104, "Myanmar", "MM", "MMR"));
        if(paisService.findOne(516) == null) paisService.save(new Pais(516, "Namibia", "NA", "NAM"));
        if(paisService.findOne(520) == null) paisService.save(new Pais(520, "Nauru", "NR", "NRU"));
        if(paisService.findOne(524) == null) paisService.save(new Pais(524, "Nepal", "NP", "NPL"));
        if(paisService.findOne(558) == null) paisService.save(new Pais(558, "Nicaragua", "NI", "NIC"));
        if(paisService.findOne(562) == null) paisService.save(new Pais(562, "Níger", "NE", "NER"));
        if(paisService.findOne(566) == null) paisService.save(new Pais(566, "Nigeria", "NG", "NGA"));
        if(paisService.findOne(570) == null) paisService.save(new Pais(570, "Niue", "NU", "NIU"));
        if(paisService.findOne(574) == null) paisService.save(new Pais(574, "Norfolk", "NF", "NFK"));
        if(paisService.findOne(578) == null) paisService.save(new Pais(578, "Noruega", "NO", "NOR"));
        if(paisService.findOne(540) == null) paisService.save(new Pais(540, "Nueva Caledonia", "NC", "NCL"));
        if(paisService.findOne(554) == null) paisService.save(new Pais(554, "Nueva Zelanda", "NZ", "NZL"));
        if(paisService.findOne(512) == null) paisService.save(new Pais(512, "Omán", "OM", "OMN"));
        if(paisService.findOne(528) == null) paisService.save(new Pais(528, "Países Bajos", "NL", "NLD"));
        if(paisService.findOne(586) == null) paisService.save(new Pais(586, "Pakistán", "PK", "PAK"));
        if(paisService.findOne(585) == null) paisService.save(new Pais(585, "Palaos", "PW", "PLW"));
        if(paisService.findOne(275) == null) paisService.save(new Pais(275, "Palestina", "PS", "PSE"));
        if(paisService.findOne(591) == null) paisService.save(new Pais(591, "Panamá", "PA", "PAN"));
        if(paisService.findOne(598) == null) paisService.save(new Pais(598, "Papúa Nueva Guinea", "PG", "PNG"));
        if(paisService.findOne(600) == null) paisService.save(new Pais(600, "Paraguay", "PY", "PRY"));
        if(paisService.findOne(604) == null) paisService.save(new Pais(604, "Perú", "PE", "PER"));
        if(paisService.findOne(258) == null) paisService.save(new Pais(258, "Polinesia Francesa", "PF", "PYF"));
        if(paisService.findOne(616) == null) paisService.save(new Pais(616, "Polonia", "PL", "POL"));
        if(paisService.findOne(620) == null) paisService.save(new Pais(620, "Portugal", "PT", "PRT"));
        if(paisService.findOne(630) == null) paisService.save(new Pais(630, "Puerto Rico", "PR", "PRI"));
        if(paisService.findOne(826) == null) paisService.save(new Pais(826, "Reino Unido", "GB", "GBR"));
        if(paisService.findOne(732) == null) paisService.save(new Pais(732, "República Árabe Saharaui Democrática", "EH", "ESH"));
        if(paisService.findOne(140) == null) paisService.save(new Pais(140, "República Centroafricana", "CF", "CAF"));
        if(paisService.findOne(203) == null) paisService.save(new Pais(203, "República Checa", "CZ", "CZE"));
        if(paisService.findOne(178) == null) paisService.save(new Pais(178, "República del Congo", "CG", "COG"));
        if(paisService.findOne(180) == null) paisService.save(new Pais(180, "República Democrática del Congo", "CD", "COD"));
        if(paisService.findOne(214) == null) paisService.save(new Pais(214, "República Dominicana", "DO", "DOM"));
        if(paisService.findOne(638) == null) paisService.save(new Pais(638, "Reunión", "RE", "REU"));
        if(paisService.findOne(646) == null) paisService.save(new Pais(646, "Ruanda", "RW", "RWA"));
        if(paisService.findOne(642) == null) paisService.save(new Pais(642, "Rumania", "RO", "ROU"));
        if(paisService.findOne(643) == null) paisService.save(new Pais(643, "Rusia", "RU", "RUS"));
        if(paisService.findOne(882) == null) paisService.save(new Pais(882, "Samoa", "WS", "WSM"));
        if(paisService.findOne(16) == null) paisService.save(new Pais(16, "Samoa Americana", "AS", "ASM"));
        if(paisService.findOne(652) == null) paisService.save(new Pais(652, "San Bartolomé", "BL", "BLM"));
        if(paisService.findOne(659) == null) paisService.save(new Pais(659, "San Cristóbal y Nieves", "KN", "KNA"));
        if(paisService.findOne(674) == null) paisService.save(new Pais(674, "San Marino", "SM", "SMR"));
        if(paisService.findOne(663) == null) paisService.save(new Pais(663, "San Martín", "MF", "MAF"));
        if(paisService.findOne(666) == null) paisService.save(new Pais(666, "San Pedro y Miquelón", "PM", "SPM"));
        if(paisService.findOne(670) == null) paisService.save(new Pais(670, "San Vicente y las Granadinas", "VC", "VCT"));
        if(paisService.findOne(654) == null) paisService.save(new Pais(654, "Santa Elena, Ascensión y Tristán de Acuña", "SH", "SHN"));
        if(paisService.findOne(662) == null) paisService.save(new Pais(662, "Santa Lucía", "LC", "LCA"));
        if(paisService.findOne(678) == null) paisService.save(new Pais(678, "Santo Tomé y Príncipe", "ST", "STP"));
        if(paisService.findOne(686) == null) paisService.save(new Pais(686, "Senegal", "SN", "SEN"));
        if(paisService.findOne(688) == null) paisService.save(new Pais(688, "Serbia", "RS", "SRB"));
        if(paisService.findOne(690) == null) paisService.save(new Pais(690, "Seychelles", "SC", "SYC"));
        if(paisService.findOne(694) == null) paisService.save(new Pais(694, "Sierra Leona", "SL", "SLE"));
        if(paisService.findOne(702) == null) paisService.save(new Pais(702, "Singapur", "SG", "SGP"));
        if(paisService.findOne(534) == null) paisService.save(new Pais(534, "Sint Maarten", "SX", "SXM"));
        if(paisService.findOne(760) == null) paisService.save(new Pais(760, "Siria", "SY", "SYR"));
        if(paisService.findOne(706) == null) paisService.save(new Pais(706, "Somalia", "SO", "SOM"));
        if(paisService.findOne(144) == null) paisService.save(new Pais(144, "Sri Lanka", "LK", "LKA"));
        if(paisService.findOne(748) == null) paisService.save(new Pais(748, "Suazilandia", "SZ", "SWZ"));
        if(paisService.findOne(710) == null) paisService.save(new Pais(710, "Sudáfrica", "ZA", "ZAF"));
        if(paisService.findOne(729) == null) paisService.save(new Pais(729, "Sudán", "SD", "SDN"));
        if(paisService.findOne(728) == null) paisService.save(new Pais(728, "Sudán del Sur", "SS", "SSD"));
        if(paisService.findOne(752) == null) paisService.save(new Pais(752, "Suecia", "SE", "SWE"));
        if(paisService.findOne(756) == null) paisService.save(new Pais(756, "Suiza", "CH", "CHE"));
        if(paisService.findOne(740) == null) paisService.save(new Pais(740, "Surinam", "SR", "SUR"));
        if(paisService.findOne(744) == null) paisService.save(new Pais(744, "Svalbard y Jan Mayen", "SJ", "SJM"));
        if(paisService.findOne(764) == null) paisService.save(new Pais(764, "Tailandia", "TH", "THA"));
        if(paisService.findOne(158) == null) paisService.save(new Pais(158, "Taiwán (República de China)", "TW", "TWN"));
        if(paisService.findOne(834) == null) paisService.save(new Pais(834, "Tanzania", "TZ", "TZA"));
        if(paisService.findOne(762) == null) paisService.save(new Pais(762, "Tayikistán", "TJ", "TJK"));
        if(paisService.findOne(86) == null) paisService.save(new Pais(86, "Territorio Británico del Océano Índico", "IO", "IOT"));
        if(paisService.findOne(260) == null) paisService.save(new Pais(260, "Tierras Australes y Antárticas Francesas", "TF", "ATF"));
        if(paisService.findOne(626) == null) paisService.save(new Pais(626, "Timor Oriental", "TL", "TLS"));
        if(paisService.findOne(768) == null) paisService.save(new Pais(768, "Togo", "TG", "TGO"));
        if(paisService.findOne(772) == null) paisService.save(new Pais(772, "Tokelau", "TK", "TKL"));
        if(paisService.findOne(776) == null) paisService.save(new Pais(776, "Tonga", "TO", "TON"));
        if(paisService.findOne(780) == null) paisService.save(new Pais(780, "Trinidad y Tobago", "TT", "TTO"));
        if(paisService.findOne(788) == null) paisService.save(new Pais(788, "Túnez", "TN", "TUN"));
        if(paisService.findOne(795) == null) paisService.save(new Pais(795, "Turkmenistán", "TM", "TKM"));
        if(paisService.findOne(792) == null) paisService.save(new Pais(792, "Turquía", "TR", "TUR"));
        if(paisService.findOne(798) == null) paisService.save(new Pais(798, "Tuvalu", "TV", "TUV"));
        if(paisService.findOne(804) == null) paisService.save(new Pais(804, "Ucrania", "UA", "UKR"));
        if(paisService.findOne(800) == null) paisService.save(new Pais(800, "Uganda", "UG", "UGA"));
        if(paisService.findOne(858) == null) paisService.save(new Pais(858, "Uruguay", "UY", "URY"));
        if(paisService.findOne(860) == null) paisService.save(new Pais(860, "Uzbekistán", "UZ", "UZB"));
        if(paisService.findOne(548) == null) paisService.save(new Pais(548, "Vanuatu", "VU", "VUT"));
        if(paisService.findOne(336) == null) paisService.save(new Pais(336, "Vaticano, Ciudad del", "VA", "VAT"));
        if(paisService.findOne(862) == null) paisService.save(new Pais(862, "Venezuela", "VE", "VEN"));
        if(paisService.findOne(704) == null) paisService.save(new Pais(704, "Vietnam", "VN", "VNM"));
        if(paisService.findOne(876) == null) paisService.save(new Pais(876, "Wallis y Futuna", "WF", "WLF"));
        if(paisService.findOne(887) == null) paisService.save(new Pais(887, "Yemen", "YE", "YEM"));
        if(paisService.findOne(262) == null) paisService.save(new Pais(262, "Yibuti", "DJ", "DJI"));
        if(paisService.findOne(894) == null) paisService.save(new Pais(894, "Zambia", "ZM", "ZMB"));
        if(paisService.findOne(716) == null) paisService.save(new Pais(716, "Zimbabue", "ZW", "ZWE"));
    }

    public void addingDisciplinas(){
        if(disciplinaService.findOne(1) == null) disciplinaService.save(new Disciplina("Running"));
        if(disciplinaService.findOne(2) == null) disciplinaService.save(new Disciplina("Ciclismo"));
        if(disciplinaService.findOne(3) == null) disciplinaService.save(new Disciplina("Natación"));
        if(disciplinaService.findOne(4) == null) disciplinaService.save(new Disciplina("Boxeo"));
        if(disciplinaService.findOne(5) == null) disciplinaService.save(new Disciplina("Clavados"));
        if(disciplinaService.findOne(6) == null) disciplinaService.save(new Disciplina("Triatlón"));
        if(disciplinaService.findOne(7) == null) disciplinaService.save(new Disciplina("Levantamiento de pesas"));
        if(disciplinaService.findOne(8) == null) disciplinaService.save(new Disciplina("Pentatlón"));
    }

    public void addingTipoTrainers(){
        if(tipoTrainerService.findOne(1) == null) tipoTrainerService.save(new TipoTrainer("Particular"));
        if(tipoTrainerService.findOne(2) == null) tipoTrainerService.save(new TipoTrainer("Empresa"));
        if(tipoTrainerService.findOne(3) == null) tipoTrainerService.save(new TipoTrainer("Asociado a empresa"));

    }

    public void addingTipoRutina(){
        if(tipoRutinaService.findOne(1) == null) tipoRutinaService.save(new TipoRutina("Running"));
        if(tipoRutinaService.findOne(2) == null) tipoRutinaService.save(new TipoRutina("General"));
    }

    public void addingUbPeru() throws IOException {
        InputStream is = new ClassPathResource("static/seeds/ub_peru.csv").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
        String content = reader.lines().collect(Collectors.joining("\n"));
        String[] lines = content.split("\n");
        reader.close();
        for(int i=1; i<lines.length;i++){
            String[] line = lines[i].split(",");
            if (ubPeruService.findById(line[0].trim()) == null) ubPeruService.save(new UbPeru(line[0].trim(), line[1].trim(), line[2].trim(), line[3].trim(), line[4].trim()));
        }
    }

    public void addingModulo() throws IOException {
        InputStream is = new ClassPathResource("static/seeds/modulo.csv").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
        String content = reader.lines().collect(Collectors.joining("\n"));
        String[] lines = content.split("\n");
        reader.close();
        for(int i=1; i<lines.length;i++){
            String[] line = lines[i].split(",");
            if (moduloService.findOneByNombre(line[0].trim()) == null) moduloService.save(new Modulo(line[0].trim(), line[1].trim()));
        }
    }

    public void addingCorreo() throws IOException {
        InputStream is = new ClassPathResource("static/seeds/correo.csv").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String content = reader.lines().collect(Collectors.joining("\n"));
        String[] lines = content.split("\n");
        reader.close();
        for(int i=1; i<lines.length;i++){
            String[] line = lines[i].split("\\|");
            if (correoService.findOne(i) == null) correoService.save(new Correo(line[0].trim(), line[1].trim(), Integer.parseInt(line[2].trim())));
        }
    }

    public void addingBanco() throws IOException {
        InputStream is = new ClassPathResource("static/seeds/banco.csv").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String content = reader.lines().collect(Collectors.joining("\n"));
        String[] lines = content.split("\n");
        reader.close();
        for(int i=1; i<lines.length;i++){
            String[] line = lines[i].split(",");
            if (bancoService.findOne(i) == null) bancoService.save(new Banco(line[0].trim()));
        }
    }



    public void addingVideo() throws IOException {
        InputStream is = new ClassPathResource("static/seeds/video.csv").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String content = reader.lines().collect(Collectors.joining("\n"));
        String[] lines = content.split("\n");
        reader.close();
        for(int i=1; i<lines.length;i++){
            String[] line = lines[i].split(",");
            if(videoService.findOne(index++) == null) videoService.save(new Video(line[0].trim(), line[1].trim(), line[2].trim(), line[3].trim(),line[4].trim(), UUID.fromString(line[5].trim()), Integer.parseInt(line[6].trim()), Boolean.valueOf(line[7].trim())));
        }
    }

    public void addingIdioma() throws IOException {
        InputStream is = new ClassPathResource("static/seeds/idioma.csv").getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String content = reader.lines().collect(Collectors.joining("\n"));
        String[] lines = content.split("\n");
        reader.close();
        for(int i=1; i<lines.length;i++){
            String[] line = lines[i].split(",");
            if (idiomaRepository.findById(i).orElse(null) == null) idiomaRepository.save(new Idioma(line[0].trim(), line[1].trim()));
        }
    }

    public void addingApplicationParameters() {
        if (parametroService.findByClave("MAIN_ROUTE") == null) {
            parametroService.add(new Parametro("MAIN_ROUTE", mainRoute));
        }
        if (parametroService.findByClave("EMAIL_RECEPTOR_CONSULTAS") == null) {
            parametroService.add(new Parametro("EMAIL_RECEPTOR_CONSULTAS", "info@runfit.pe"));
        }
    }

    public void addingToContextSession() {
        context.setAttribute("MAIN_ROUTE", parametroService.findByClave("MAIN_ROUTE").getValor());
        context.setAttribute("version", currentVersion);
    }

    public void agregandoSuperAdmin(){
        SecurityUser secUser = new SecurityUser();
        secUser.setUsername("admin@itsight.pe");
        secUser.setPassword(new BCryptPasswordEncoder().encode("itsight19@13"));
        secUser.setEnabled(true);
        //Roles
        SecurityRole role = new SecurityRole("ROLE_ADMIN");
        //Privileges
        SecurityPrivilege priv1 = new SecurityPrivilege("READ_PRIVILEGE");
        SecurityPrivilege priv2 = new SecurityPrivilege("WRITE_PRIVILEGE");
        //Set Privileges
        Set<SecurityPrivilege> setPrivileges = new HashSet<>();
        setPrivileges.add(priv1);
        setPrivileges.add(priv2);
        //Adding to Role father
        role.setPrivileges(setPrivileges);
        //Set Roles(Only 1)
        Set<SecurityRole> roles = new HashSet<>();
        roles.add(role);
        //Adding to User
        secUser.setRoles(roles);
        userRepository.save(secUser);
    }

    public List<Trainer> agregandoEntrenadores(){
        final Integer entrenadores = 6;
        List<Trainer> lstTrainerRedFitness = new ArrayList<>();
        for(int i=1; i <= entrenadores;i++){
            SecurityUser secTrainer = new SecurityUser();
            String correoUsuario = String.format("trainer%s@runfit.pe", i);
            secTrainer.setUsername(correoUsuario);
            secTrainer.setPassword(new BCryptPasswordEncoder().encode("runfit123"));
            secTrainer.setEnabled(true);
            SecurityRole role1 = new SecurityRole("ROLE_ADMIN");
            SecurityRole role2 = new SecurityRole("ROLE_TRAINER");
            SecurityRole role3 = null;
            if(i==1) role3 = new SecurityRole("ROLE_DG");
            Set<SecurityRole>  roles = new HashSet<>();
            roles.add(role1);
            roles.add(role2);
            if(i==1) roles.add(role3);
            secTrainer.setRoles(roles);
            //Añadiendole los datos detalle del entrenador(TB: Cliente)
            Trainer trainer = new Trainer(
                "Alejandro "+ i, "Gonzales Prada", correoUsuario, "543213"+i,
                 "5197672198"+i , correoUsuario, "0102030"+i, true, 1,true);
            List<com.itsight.domain.jsonb.Rol> rolesJsonB = new ArrayList<>();
            rolesJsonB.add(new com.itsight.domain.jsonb.Rol(1, role1.getRole()));
            rolesJsonB.add(new com.itsight.domain.jsonb.Rol(2, role2.getRole()));
            trainer.setRoles(rolesJsonB);
            trainer.setSecurityUser(secTrainer);
            trainer.setPais(604);//Perú!
            trainer.setUbigeo("150101");
            trainer.setCanPerValoracion(0);
            trainer.setTotalValoracion(0.0);
            TipoTrainer tp = new TipoTrainer();
            tp.setId(1);
            trainer.setTipoTrainer(tp);
            trainerService.save(trainer);
            trainerService.cargarRutinarioCe(secTrainer.getId());
            agregandoPorcentajesBaseTrainer(trainer);
            if(i<4)//Para al momento de crear los clientes estos pertenezcan a la red de estos trainers
                lstTrainerRedFitness.add(new Trainer(trainer.getId()));
        }
        return lstTrainerRedFitness;
    }

    public void agregandoPorcentajesBaseTrainer(Trainer trainer){
        //Agregando sus porcentajes base para la creación de macro-ciclos
        PorcentajesKilometraje porcentajes;
        Integer[] maratonDistancias = {10, 21, 42};

        for(int i=0; i<3; i++){
            porcentajes = new PorcentajesKilometraje();
            porcentajes.setDistancia(maratonDistancias[i]);
            porcentajes.setTrainer(trainer);
            List<PorcKiloTipo> lstPorcKiloTipo = new ArrayList<>();
            for(int k=1; k<4; k++){
                PorcKiloTipo porcKiloTipo = new PorcKiloTipo();
                porcKiloTipo.setTipo(k);
                List<PorcKiloTipoSema> lstPorcKiloTipoSema = new ArrayList<>();
                for(int y=2; y<29; y++) {
                    PorcKiloTipoSema porcKiloTipoSema = new PorcKiloTipoSema();
                    porcKiloTipoSema.setTs(y);
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

    public void agregandoClientes(List<Trainer> trainers){
        //Agregando cliente a entrenador creado
        final Integer cantidadCli = 9;
        for(int i=1; i <= cantidadCli; i++){
            SecurityUser secCliente = new SecurityUser();
            String correoUsuario = String.format("runner%s@runfit.pe", i);
            secCliente.setUsername(correoUsuario);
            secCliente.setPassword(new BCryptPasswordEncoder().encode("runfit123"));
            secCliente.setEnabled(true);

            Set<SecurityRole> lstRolesCli = new HashSet<>();
            if(i==1) lstRolesCli.add(new SecurityRole("ROLE_DG_CLI"));
            lstRolesCli.add(new SecurityRole("ROLE_RUNNER"));
            secCliente.setRoles(lstRolesCli);
            Cliente cli = new Cliente(
                    "Jorge "+ i, "Almendariz Molina", correoUsuario,
                    "5198765432"+i , correoUsuario, "4444444"+i, 1,true);
            List<com.itsight.domain.jsonb.Rol> lstRolCli = new ArrayList<>();
            lstRolCli.add(new com.itsight.domain.jsonb.Rol(3, "ROLE_RUNNER"));
            cli.setPais(604);//Perú!
            cli.setUbigeo("150101");
            cli.setRoles(lstRolCli);
            cli.setFechaNacimiento(Parseador.fromStringToDate("1987-01-0"+i));
            cli.setSecurityUser(secCliente);

            //
            ConfiguracionCliente cliConfg = new ConfiguracionCliente();
            cliConfg.setLstParametro(configuracionGeneralService.findAll().stream()
                    .map(cg-> new com.itsight.domain.jsonb.Parametro(
                                cg.getNombre(),
                                cg.getValor()))
                                .collect(Collectors.toList()));
            cliConfg.setCliente(cli);
            //Registrando en cascada SEC_USER->CLIENTE->CONFIG_CLIENTE (OneToOne relationships)
            configuracionClienteService.save(cliConfg);

            //Agregando su información fitness básica
            ClienteFitness cliFit = new ClienteFitness();
            cliFit.setNivel((int) Math.ceil((double)i/3.0));
            cliFit.setEstadoCivil(1);
            cliFit.setImc(Double.parseDouble(String.valueOf(15+i)));
            cliFit.setPeso(BigDecimal.valueOf(65+i));
            cliFit.setSexo(1);
            cliFit.setDesgasteZapatilla("Inicio");
            cliFit.setDesObjetivos("Resistencia");
            cliFit.setDesTerPredom("Asfalto");
            CondicionAnatomica ca = new CondicionAnatomica();
            ca.setFrecuenciaCardiaca(65+i);
            ca.setFrecuenciaCardiacaMaxima(190-i);
            cliFit.setCondicionAnatomica(ca);
            cliFit.setSalud(new Salud());
            cliFit.setKilometrajePromedioSemana(BigDecimal.valueOf(20));
            cliFit.setMejoras(new ArrayList<>());
            cliFit.setFrecuenciaComunicacion(1);
            cliFit.setDiasSemanaCorriendo(1);
            cliFit.setViaConexion(1);
            cliFit.setTalla(166);
            cliFit.setTiempoDistancia("{'2':'01:10:01','4':'01:10:01','21':'01:10:01','42':'01:10:01'}");
            List<CompetenciaRunner> comps = new ArrayList<>();
            Integer[] distancias = {10,21,42};
            //String[] fechas = {"2019-02-10","2019-03-10","2019-04-27"};
            String[] tiempos = {"00:58", "02:10", "04:12"};
            for(int k=1; k<4;k++){
                CompetenciaRunner cr = new CompetenciaRunner();
                cr.setDistancia(distancias[k-1]+0.0);
                cr.setFecha(new Date());
                cr.setNombre("Maratón "+k);
                cr.setPrioridad(2);
                cr.setTiempoObjetivo(tiempos[k-1]);
                comps.add(cr);
            }
            comps.get(2).setPrioridad(1);
            cliFit.setCompetencias(comps);
            List<FitElemento> fitElementos = new ArrayList<>();
            fitElementos.add(new FitElemento(1, "Pulsómetro"));
            fitElementos.add(new FitElemento(2, "Ligas"));

            cliFit.setFitElementos(fitElementos);
            cliFit.setCliente(cli);
            clienteFitnessService.save(cliFit);
            if(i<4){
                trainers.forEach(t ->{
                    RedFitness rf = new RedFitness(t.getId(), cli.getId(), "");
                    rf.setPredeterminadaFichaId(1);
                    redFitnessService.save(rf);
                });
            }
        }
    }

    public void addingInitUsers() {
        SecurityUser securityUser = userRepository.findByUsername("admin@itsight.pe");
        if (securityUser == null) {
            agregandoSuperAdmin();
            agregandoClientes(agregandoEntrenadores());
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
                "/Multimedia",
        };
        Utilitarios.createDirectoryStartUp(context.getAttribute("MAIN_ROUTE").toString(), childPaths);
    }
}
