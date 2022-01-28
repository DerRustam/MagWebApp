package com.rustam.magbackend.setup;


import com.rustam.magbackend.composite.PictureImageId;
import com.rustam.magbackend.configuration.RoleConfiguration;
import com.rustam.magbackend.enums.ImageTypeSize;
import com.rustam.magbackend.enums.PersonGender;
import com.rustam.magbackend.model.*;
import com.rustam.magbackend.repository.*;
import com.rustam.magbackend.utils.compression.ImageCompressionUtil;
import com.rustam.magbackend.utils.compression.PreparedImage;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class SetupDataLoader{

    private static final Logger log = LoggerFactory.getLogger(SetupDataLoader.class);

    @Bean
    @Transactional
    public CommandLineRunner initDatabase(
            RoleConfiguration roleConfiguration,
            IRoleRepository roleRepository,
            IPrivilegeRepository privilegeRepository,
            IAccountRepository accountRepository,
            IAlbumRepository albumRepository,
            IPublicationRepository publicationRepository,
            IPictureImageRepository pictureImageRepository,
            ISearchTagRepository searchTagRepository,
            IMatureRatingRepository matureRatingRepository,
            IHistoryRepository historyRepository,
            PasswordEncoder passwordEncoder) throws RuntimeException{

        setupRolesPrivileges(roleRepository, privilegeRepository,roleConfiguration);
        setupSearchTags(searchTagRepository);
        setupMatureRatings(matureRatingRepository);
        setupHeadAdminAccount(accountRepository,roleRepository, passwordEncoder,roleConfiguration);
        UserAccount ph = setupPlaceholderUser(accountRepository, roleRepository, passwordEncoder,roleConfiguration);
        setupPlaceholderAlbum(ph, albumRepository);
        UserAccount ua = setupTestUser(accountRepository, roleRepository, passwordEncoder,roleConfiguration);
        Album album = setupTestUserAlbum(ua, albumRepository);
        Picture p = setupTestUserPicture(album, publicationRepository, searchTagRepository, matureRatingRepository, pictureImageRepository);
        setupTestNode(album, publicationRepository, searchTagRepository, matureRatingRepository);
        setupTestUserHistory(ua, p, historyRepository);
        return args -> {
            log.info("Initial Dataset deployed.");
        };
    }



    @Transactional
    protected void setupRolesPrivileges(IRoleRepository roleRepository, IPrivilegeRepository privilegeRepository, RoleConfiguration config){
        Privilege watchAll = addPrivilegeIfNotExists(privilegeRepository, config.getPRIVILEGE_WATCH_MATURE(), "Can watch mature publications");
        Privilege publishSafe = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_PUBLISH_SAFE(), "Can publish safe publications");
        Privilege publishAll = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_PUBLISH_MATURE(), "Can publish mature publications");
        Privilege pubRD = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_PUBLICATION_RD(),"Can read publications data");
        Privilege pubWR = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_PUBLICATION_WR(),"Can write/update publications data");
        Privilege uRD = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_USER_RD(),"Can read users data");
        Privilege uWR = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_USER_WR(),"Can write/update users data");
        Privilege sRD = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_STAFF_RD(),"Can read staff data");
        Privilege sWR = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_STAFF_WR(),"Can write/update staff data");
        Privilege strRD = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_STRUCT_RD(),"Can read role/privilege data");
        Privilege strWR = addPrivilegeIfNotExists(privilegeRepository,config.getPRIVILEGE_STRUCT_WR(),"Can write/update role/privilege data");

        addRoleIfNotExists(roleRepository,config.getROLE_USER_WATCH_SAFE(), "User role. Can watch only safe age rating publications",
                new ArrayList<>());
        addRoleIfNotExists(roleRepository,config.getROLE_USER_WATCH_ALL(), "User role. Can watch all age rating publications",
                new ArrayList<Privilege>(){{add(watchAll);}});
        addRoleIfNotExists(roleRepository, config.getROLE_USER_PUBLISH_SAFE(), "User role. Can Watch/Publish safe age rating publications",
                new ArrayList<Privilege>(){{add(publishSafe);}});
        addRoleIfNotExists(roleRepository,config.getROLE_USER_PUBLISH_ALL(), "User role. Can Watch/Publish all age rating publications",
                new ArrayList<Privilege>(){{add(watchAll); add(publishSafe); add(publishAll);}});
        addRoleIfNotExists(roleRepository,config.getROLE_STAFF_MODERATOR(), "Staff member with moderating publications privilege",
                new ArrayList<Privilege>(){{add(watchAll); add(pubRD); add(pubWR);}});
        addRoleIfNotExists(roleRepository,config.getROLE_STAFF_ADMIN(), "Staff member with writing accounts and moderating publications privilege",
                new ArrayList<Privilege>(){{add(watchAll); add(pubRD); add(pubWR); add(uRD);add(uWR);}});
        addRoleIfNotExists(roleRepository,config.getROLE_STAFF_HEAD_ADMIN(), "Head role. Can update account(users and staff)/publication data as well as Role/Privilege restrictions",
                new ArrayList<Privilege>(){{add(watchAll); add(pubRD); add(pubWR); add(uRD);add(uWR); add(sRD); add(sWR); add(strRD); add(strWR);}});
    }

    @Transactional
    protected void setupSearchTags(ISearchTagRepository repository){
        addSearchTagIfNotExists(repository, "test_tag");
        addSearchTagIfNotExists(repository, "unknown");
    }

    @Transactional
    protected void setupMatureRatings(IMatureRatingRepository repository){
        addMatureRatingIfNotExists(repository, "Test", "Test Content");
        addMatureRatingIfNotExists(repository, "R-18", "Mature Content");
        addMatureRatingIfNotExists(repository, "R-18G", "Very Mature Content");
    }

    @Transactional
    protected void setupHeadAdminAccount(IAccountRepository accountRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, RoleConfiguration config){
        Role role = roleRepository.findRoleByNameRole(config.getROLE_STAFF_HEAD_ADMIN()).orElseThrow(RuntimeException::new);
        Optional<Account> opAcc = accountRepository.findAccountByEmail("superadmin@staff.com");
        if (!opAcc.isPresent()){
            StaffAccount account = new StaffAccount(
                    "superadmin",
                    "superadmin@staff.com",
                    passwordEncoder.encode("niggerfag"),
                    false,
                    new ArrayList<Role>(){{add(role);}},
                    "Claus01",
                    "superadmin@staff.com",
                    "+7-111-222-44-55",
                    "Claus Right Lemone",
                    Date.valueOf("2014-10-10"),
                    Date.valueOf("1990-11-22"),
                    PersonGender.M
            );
            accountRepository.save(account);
        }
    }

    @Transactional
    UserAccount setupPlaceholderUser(IAccountRepository accountRepository,
                                     IRoleRepository roleRepository,
                                     PasswordEncoder passwordEncoder,
                                     RoleConfiguration config){
        UserAccount ua;
        Optional<Account> opAcc = accountRepository.findAccountByUsername("void_archive_zero");
        if (!opAcc.isPresent()){
            Role role = roleRepository.findRoleByNameRole(config.getROLE_USER_PUBLISH_ALL()).orElseThrow(RuntimeException::new);
            UserAccount account = new UserAccount(
                    0L,
                    "void_archive_zero",
                    "void_archive_zero@void_archive_zero.com",
                    passwordEncoder.encode("y0uWillNeverKnow"),
                    true,
                    new ArrayList<Role>(){{add(role);}},
                    "??",
                    "Void",
                    "Archive",
                    Date.valueOf("2000-01-01"),
                    null,
                    "Void Archive"
            );
            ua = accountRepository.save(account);
        } else {
            ua = (UserAccount) opAcc.get();
        }
        return ua;
    }

    @Transactional
    protected Album setupPlaceholderAlbum(UserAccount account, IAlbumRepository repository) throws RuntimeException{
        Album a;
        Optional<Album> opA = repository.findAlbumByUserAccountAndNameAlbum(account, "Void Archive");
        if (!opA.isPresent()){
            Album album = new Album(0L,account, "Void Archive", Date.valueOf(LocalDate.now()), "Void Archive album");
            a = repository.save(album);
        } else {
            a = opA.get();
        }
        return a;
    }

    @Transactional
    UserAccount setupTestUser(
            IAccountRepository accountRepository,
            IRoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            RoleConfiguration config){
        Role role = roleRepository.findRoleByNameRole(config.getROLE_USER_PUBLISH_ALL()).orElseThrow(RuntimeException::new);
        UserAccount ua;
        Optional<Account> opAcc = accountRepository.findAccountByEmail("sparkdude@gmail.com");
        if (!opAcc.isPresent()){
            UserAccount account = new UserAccount(
                    "sparkie",
                    "sparkdude@gmail.com",
                    passwordEncoder.encode("qwer1234"),
                    false,
                    new ArrayList<Role>(){{add(role);}},
                    "Spark",
                    "Simon",
                    null,
                    Date.valueOf("2007-01-16"),
                    PersonGender.M,
                    null
            );
            ua = accountRepository.save(account);
        } else {
            ua = (UserAccount) opAcc.get();
        }
        return ua;
    }


    @Transactional
    protected Album setupTestUserAlbum(UserAccount account, IAlbumRepository repository) throws RuntimeException{
        Album a;
        Optional<Album> opA = repository.findAlbumByUserAccountAndNameAlbum(account, "Default");
        if (!opA.isPresent()){
            Album album = new Album(account, "Default", Date.valueOf(LocalDate.now()), "Default album");
            a = repository.save(album);
        } else {
            a = opA.get();
        }
        return a;
    }


    @Transactional
    protected Picture setupTestUserPicture(Album album,
                                         IPublicationRepository publicationRepository,
                                         ISearchTagRepository searchTagRepository,
                                         IMatureRatingRepository matureRatingRepository,
                                         IPictureImageRepository pictureImageRepository) throws RuntimeException{
        SearchTag tag = searchTagRepository.findSearchTagByNameTag("test_tag").orElseThrow(RuntimeException::new);
        MatureRating rating = matureRatingRepository.findMatureRatingByNameRating("Test").orElseThrow(RuntimeException::new);
        PreparedImage preparedImage;
        try{
            File file = new ClassPathResource("static/test_img.PNG").getFile();
            int size = (int)file.length();
            FileItem fileItem = new DiskFileItem(
                    "testImg",
                    Files.probeContentType(file.toPath()),
                    false, file.getName(),
                    size,
                    file.getParentFile());
            InputStream is = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            int ret = is.read();
            while (ret != -1){
                os.write(ret);
                ret = is.read();
            }
            os.flush();
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            ImageCompressionUtil util = new ImageCompressionUtil();
            preparedImage = util.prepareImage(multipartFile);
        } catch (IOException ex){
            throw new RuntimeException(ex.getMessage());
        }
        Picture p;
        Optional<Publication> opPic = publicationRepository.findPublicationByAlbumAndNamePublication(album, "Test Picture");
        if (!opPic.isPresent()){
            p = new Picture(
                    album,
                    "Test Picture",
                    rating,
                    true,
                    false,
                    Date.valueOf(LocalDate.now()),
                    new ArrayList<SearchTag>(){{add(tag);}},
                    "png",
                    "test description");
            p = publicationRepository.save(p);
        } else {
            p = (Picture)opPic.get();
        }

        Optional<PictureImage> opP = pictureImageRepository.findById(new PictureImageId(p.getId(), ImageTypeSize.THUMBNAIL));
        if (!opP.isPresent()){
            PictureImage thumb = new PictureImage(p.getId(), ImageTypeSize.THUMBNAIL, preparedImage.getThumb());
            pictureImageRepository.save(thumb);
        }
        if (preparedImage.getView() != null){
            opP = pictureImageRepository.findById(new PictureImageId(p.getId(), ImageTypeSize.VIEW));
            if (!opP.isPresent() || opP.get().getImage().length != preparedImage.getView().length){
                PictureImage view = new PictureImage(p.getId(), ImageTypeSize.VIEW, preparedImage.getView());
                pictureImageRepository.save(view);
            }
        }
        if (preparedImage.getFull() != null){
            opP = pictureImageRepository.findById(new PictureImageId(p.getId(), ImageTypeSize.FULL));
            if (!opP.isPresent() || opP.get().getImage().length != preparedImage.getView().length){
                PictureImage full = new PictureImage(p.getId(), ImageTypeSize.FULL, preparedImage.getFull());
                pictureImageRepository.save(full);
            }
        }
        return p;
    }

    @Transactional
    protected Note setupTestNode(Album album,
                                 IPublicationRepository publicationRepository,
                                 ISearchTagRepository searchTagRepository,
                                 IMatureRatingRepository matureRatingRepository) throws RuntimeException{
        SearchTag tag = searchTagRepository.findSearchTagByNameTag("test_tag").orElseThrow(RuntimeException::new);
        MatureRating rating = matureRatingRepository.findMatureRatingByNameRating("Test").orElseThrow(RuntimeException::new);
        Note n;
        Optional<Publication> opN = publicationRepository.findPublicationByAlbumAndNamePublication(album, "Test Note");
        if (!opN.isPresent()){
            n = new Note(album,
                    "Test Note",
                    rating,
                    true,
                    false,
                    Date.valueOf(LocalDate.now()),
                    new ArrayList<SearchTag>(){{add(tag);}},
                    "Note Text ...");
            n = publicationRepository.save(n);
        } else {
            n = (Note)opN.get();
        }
        return n;
    }

    @Transactional
    protected History setupTestUserHistory(Account a, Publication p, IHistoryRepository repository){
        History history;
        Optional<History> opH = repository.findByAccountAndPublication(a, p);
        if (!opH.isPresent()){
            history = new History(a, p, Timestamp.from(Instant.now()), "Test description history");
            repository.save(history);
        } else {
            history = opH.get();
        }
        return history;
    }

    Privilege addPrivilegeIfNotExists(IPrivilegeRepository privilegeRepository, String namePrivilege, String description){
        Privilege privilege;
        Optional<Privilege> opP = privilegeRepository.findPrivilegeByNamePrivilege(namePrivilege);
        if (!opP.isPresent()){
            privilege = new Privilege(namePrivilege, description, new ArrayList<>());
            privilege = privilegeRepository.save(privilege);
        } else {
            privilege = opP.get();
            if (!privilege.getDescriptionPrivilege().equals(description)){
                privilege.setDescriptionPrivilege(description);
                privilege = privilegeRepository.save(privilege);
            }
        }
        return privilege;
    }


    Role addRoleIfNotExists(IRoleRepository roleRepository, String nameRole, String description, List<Privilege> privileges){
        Role role;
        Optional<Role> opR = roleRepository.findRoleByNameRole(nameRole);
        if (!opR.isPresent()){
            role = new Role(nameRole, description, new ArrayList<>(), privileges);
            role = roleRepository.save(role);
        } else {
            role = opR.get();
            boolean changed = false;
            if (!role.getDescriptionRole().equals(description)){
                role.setDescriptionRole(description);
                changed = true;
            }
            if (!isListsEquals(role.getPrivileges(), privileges)){
                role.setPrivileges(privileges);
                changed = true;
            }
            if (changed){
                role = roleRepository.save(role);
            }
        }
        return role;
    }


    protected SearchTag addSearchTagIfNotExists(ISearchTagRepository repository, String tagName){
        SearchTag tag;
        Optional<SearchTag> opS = repository.findSearchTagByNameTag(tagName);
        if (!opS.isPresent()){
            tag = new SearchTag();
            tag.setNameTag(tagName);
            tag = repository.save(tag);
        } else {
            tag = opS.get();
        }
        return tag;
    }


    protected MatureRating addMatureRatingIfNotExists(IMatureRatingRepository repository, String ageRatingName, String ageRatingDescription){
        MatureRating matureRating;
        Optional<MatureRating> opA = repository.findMatureRatingByNameRating(ageRatingName);
        if (!opA.isPresent()){
            matureRating = new MatureRating(ageRatingName, ageRatingDescription);
            matureRating = repository.save(matureRating);
        } else {
            matureRating = opA.get();
            if (!matureRating.getDescriptionRating().equals(ageRatingDescription)){
                matureRating.setDescriptionRating(ageRatingDescription);
                matureRating = repository.save(matureRating);
            }
        }
        return matureRating;
    }

    protected boolean isListsEquals(List<Privilege> list1, List<Privilege> list2){
        if (list1.size() != list2.size()){
            return false;
        }
        for (int i = 0; i< list1.size(); i++){
            if (!list1.get(i).getNamePrivilege().equals(list2.get(i).getNamePrivilege())){
                return false;
            }
        }
        return true;
    }
}
