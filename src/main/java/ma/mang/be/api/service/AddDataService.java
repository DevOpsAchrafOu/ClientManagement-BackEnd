package ma.mang.be.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ma.mang.be.api.entity.Menu;
import ma.mang.be.api.entity.ParPays;
import ma.mang.be.api.entity.ParVille;
import ma.mang.be.api.entity.Role;
import ma.mang.be.api.entity.Utilisateur;
import ma.mang.be.api.repository.MenuRepository;
import ma.mang.be.api.repository.ParPaysRepository;
import ma.mang.be.api.repository.ParVilleRepository;
import ma.mang.be.api.repository.RoleRepository;
import ma.mang.be.api.repository.UtilisateurRepository;


@Component
public class AddDataService implements CommandLineRunner {

	@Autowired
	UtilisateurRepository userRepo;
	
	@Autowired
	MenuRepository menuRepo;
	
	@Autowired
	ParPaysRepository parPaysRepo;
	
	@Autowired
	ParVilleRepository parVilleRepo;
	
	@Autowired
	RoleRepository roleRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		// ---------------------Insert data ------------------------//

		// tester si admin existe pour objectif de inserte les data dans BD
		boolean adminExist = userRepo.findByLogin("a.admin") != null ? false : true;

		// créer les data is true
		if (adminExist) {
			
			System.out.println("add Data for DataBase");

			/*--------------- add Menu----------------*/
			menuRepo.save(new Menu(1, "fa fa-bar-chart", 1, "الصفحة الرئيسية", "Accueil", "V", "/home", 0));
	        menuRepo.save(new Menu(2, "fa fa-cogs", 2, "ادارة", "Administration", "V", "#", 0));
	        menuRepo.save(new Menu(3, "fa fa-users", 3, "المستخدمون", "Utilisateurs", "V", "/administration/list-user", 2));
	        menuRepo.save(new Menu(4, "fa fa-id-card", 4, "الأدوار", "Rôles", "V", "/administration/list-role", 2));
	        menuRepo.save(new Menu(5, "fa fa-list-alt", 5, "القوائم", "Menus", "V", "/administration/list-menu", 2));
	        menuRepo.save(new Menu(6, "fa fa-database", 6, "إعدادت", "Paramètres", "V", "#", 0));
	        menuRepo.save(new Menu(7, "fa fa-globe", 7, "البلدان", "Pays", "V", "/parametrage/list-pays", 6));
	        menuRepo.save(new Menu(8, "fa fa-map-pin", 8, "المدن", "Villes", "V", "/parametrage/list-ville", 6));
			
			/*--------------- add Role-Menu----------------*/
			// Create a List of Role objects
			Role role1 = new Role( "Super Admin", "ROLE_SUPER_ADMIN");
			List<Menu> menus = new ArrayList<Menu>();
			for (int i = 1; i < 9; i++) {
				menus.add(new Menu(i));
			}
			
			role1.setMenu(menus);
			Role role2 = new Role( "Administrateur", "ROLE_ADMIN");
			Role role3 = new Role( "Utilisateur", "ROLE_USER");
			Role role4 = new Role( "Manger", "ROLE_MANGER");
			
			roleRepo.save(role1);
			roleRepo.save(role2);
			roleRepo.save(role3);
			roleRepo.save(role4);
			
			/*--------------- add Utilisateur----------------*/
	        userRepo.save(new Utilisateur(1, new Date(1675179447000L), "a.achraf@user.com", "a.admin", "Ouakka", "$2a$10$ZdwjV.jupvdRgb2Hlj1W1eexaF3GXxNZXvQY6asABYZYaIscWR1RW", "0687453209", "Admin", "ACTIVATED", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhLmFjaHJhZiIsInJvbGUiOiJST0xFX1NVUEVSX0FETUlOIiwiZXhwIjoxNjgxMjI0MDEyfQ.NaiuiL9TtBddD8o5ikjZ5p7_z1tl5AKnEtAxyUMU5R9eZnNhZq1m7yZM_k5-hNHLRasCT0gOIh60oFAoXvkXFg", 1));
	        userRepo.save(new Utilisateur(2, new Date(1675180146000L), "a.anass@user.com", "m.anass", "Malki", "$2a$10$ZdwjV.jupvdRgb2Hlj1W1eexaF3GXxNZXvQY6asABYZYaIscWR1RW", "0687453209", "Anass", "ACTIVATED", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhLmFuYXNzIiwicm9sZSI6IlJPTEVfTUFOR0VSIiwiZXhwIjoxNjc0NTY5MzQ2fQ.9Mdf5g8RDUFkzme-kUCNdC5oTN73qmWxXCLxd95e_Xz1ylVELJMgVt-SVwFr0ch92oeBZx48GEdqQdddvANUrA", 2));
	        userRepo.save(new Utilisateur(3, new Date(1678798759000L), "ahmad@user.com", "s.ahmed", "salamat", "$2a$10$e5qpyIl2hSBhFL7hnohUPuecWOOYfT1V70t/PYndYoBW2lQCkohJe", "0667456323", "Ahmad", "ACTIVATED", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhLmFuYXNzIiwicm9sZSI6IlJPTEVfTUFOR0VSIiwiZXhwIjoxNjc0NTY5MzQ2fQ.9Mdf5g8RDUFkzme-kUCNdC5oTN73qmWxXCLxd95e_Xz1ylVELJMgVt-SVwFr0ch92oeBZx48GEdqQdddvANUrA", 3));

			/*--------------- add Pays----------------*/
	        parPaysRepo.save(new ParPays(1, "المغرب", "Maroc"));
	        parPaysRepo.save(new ParPays(2, "فرنسا", "France"));
	        parPaysRepo.save(new ParPays(3, "إسبانيا", "Espagne"));
	        parPaysRepo.save(new ParPays(4, "الجزائر", "Algerie"));
	        parPaysRepo.save(new ParPays(5, "تونس", "Tunisie"));
			
			/*--------------- add Ville----------------*/
			
	        parVilleRepo.save(new ParVille(1, 1, "البيضاء أنفا", "Casablanca Anfa"));
	        parVilleRepo.save(new ParVille(2, 1, "الجديدة", "El Jadida"));
	        parVilleRepo.save(new ParVille(3, 1, "المحمدية", "Mohammadia"));
	        parVilleRepo.save(new ParVille(4, 1, "الخميسات", "Khemisset"));
	        parVilleRepo.save(new ParVille(5, 1, "الرباط", "Rabat"));
	        parVilleRepo.save(new ParVille(6, 1, "القنيطرة", "Kenitra"));
	        parVilleRepo.save(new ParVille(7, 1, "تمارة - الصخيرات", "Skhirate Temara"));
	        parVilleRepo.save(new ParVille(8, 1, "سلا", "Salé"));
	        parVilleRepo.save(new ParVille(9, 1, "العيون", "Laayoune"));
	        parVilleRepo.save(new ParVille(10, 1, "أكادير إداوتنان", "Agadir Ida Outanane"));
	        parVilleRepo.save(new ParVille(11, 1, "تطوان", "Tetouan"));
	        parVilleRepo.save(new ParVille(12, 1, "إفران", "Ifrane"));
	        parVilleRepo.save(new ParVille(13, 1, "فاس", "Fès"));
	        parVilleRepo.save(new ParVille(14, 1, "مكناس", "Meknès"));
	        parVilleRepo.save(new ParVille(15, 1, "مراكش", "Marrakech"));
	        parVilleRepo.save(new ParVille(16, 2, "باريس", "Paris"));
	        parVilleRepo.save(new ParVille(17, 2, "مرسيليا", "Marseille"));
	        parVilleRepo.save(new ParVille(18, 2, "نانت", "Nantes"));
	        parVilleRepo.save(new ParVille(19, 2, "بوردو", "Bordeaux"));
	        parVilleRepo.save(new ParVille(20, 2, "تولوز", "Toulouse"));
	        parVilleRepo.save(new ParVille(21, 3, "مدريد", "Madrid"));
	        parVilleRepo.save(new ParVille(22, 3, "برشلونة", "Barcelone"));
	        parVilleRepo.save(new ParVille(23, 3, "إشبيلية", "Séville"));

	
			
		}
	}

}