import net.java.frej.Regex;

import java.util.Scanner;

/**
 * Created by Shaun on 28/10/2015.
 */


public class testing123 {
    public static void main(String[] args) {
        //to test Regex use
        Boolean running = true;
        while (running) {
            System.out.println("input destination");

            Scanner user_input = new Scanner(System.in);
            String a = user_input.nextLine();
            a=a.toLowerCase();
            a=a.replaceAll("\\s+", "");
            a=a.replaceAll("[^a-zA-Z ]", "");

            Regex test1a = new Regex("[abdulgaffoormosque]");
            Regex test1b = new Regex("[gaffoormosque]");
            Regex test1c = new Regex("[abdulmosque]");
            Regex test2a = new Regex("[mosquealabrar]");
            Regex test2b = new Regex("[alabrarmosque]");
            Regex test2c = new Regex("[abrarMosque]");
            Regex test3a = new Regex("[artsciencemuseum]");
            Regex test3b = new Regex("[artandsciencemuseum]");
            Regex test3c = new Regex("[museumartscience]");
            Regex test4a = new Regex("[asiancivilisationsmuseum]");
            Regex test4b = new Regex("[asianmuseum]");
            Regex test4c = new Regex("[asiancivilisations]");
            Regex test5a = new Regex("[brighthilltemple]");
            Regex test5b = new Regex("[khongmengsanphorkarkseetemple]");
            Regex test5c = new Regex("[khongmengtemple]");
            Regex test6a = new Regex("[buddhatoothrelictemple]");
            Regex test6b = new Regex("[toothrelictemple]");
            Regex test6c = new Regex("[buddhatoothtemple]");
            Regex test7a = new Regex("[bukittimahnaturereserve]");
            Regex test7b = new Regex("[bukittimahreserve]");
            Regex test7c = new Regex("[bukittimah]");
            Regex test8a = new Regex("[cathedralofthegoodshepherd]");
            Regex test8b = new Regex("[cathedralgoodshepherd]");
            Regex test8c = new Regex("[shepherdcathedral]");
            Regex test9a = new Regex("[centralcatchmentnaturereserve]");
            Regex test9b = new Regex("[centralnaturereserve]");
            Regex test9c = new Regex("[centralcatchmentreserve]");
            Regex test10a = new Regex("[centralsikhtemple]");
            Regex test10b = new Regex("[centraltemple]");
            Regex test10c = new Regex("[sikhtemple]");
            Regex test11a = new Regex("[changiprisonchapelandmuseum]");
            Regex test11b = new Regex("[ChangiPrison]");
            Regex test11c = new Regex("[ChangiMuseum]");
            Regex test12a = new Regex("[Chjimes]");
            Regex test12b = new Regex("[chymes]");
            Regex test12c = new Regex("[chimes]");
            Regex test13a = new Regex("[ChinatownHeritageCentre]");
            Regex test13b = new Regex("[Chinatown]");
            Regex test13c = new Regex("[ChinaHeritage]");
            Regex test14a = new Regex("[ChineseandJapaneseGardens]");
            Regex test14b = new Regex("[ChineseGardens]");
            Regex test14c = new Regex("[JapaneseGardens]");
            Regex test15a = new Regex("[ChineseMethodistChurch]");
            Regex test15b = new Regex("[ChineseChurch]");
            Regex test15c = new Regex("[ChineseMethodist]");
            Regex test16a = new Regex("[ChurchofStGregorytheIlluminator]");
            Regex test16b = new Regex("[St.GregoryChurch]");
            Regex test16c = new Regex("[ChurchofSaintGregory]");
            Regex test17a = new Regex("[CraneDance]");
            Regex test17b = new Regex("[DanceCrane]");
            Regex test17c = new Regex("[CraneDancing]");
            Regex test18a = new Regex("[EastCoastPark]");
            Regex test18b = new Regex("[ECPark]");
            Regex test18c = new Regex("[EastPark]");
            Regex test19a = new Regex("[EsplanadeTheatresontheBay]");
            Regex test19b = new Regex("[Esplanade]");
            Regex test19c = new Regex("[TheatresontheBay]");
            Regex test20a = new Regex("[EuYanSangChineseMedicalHall]");
            Regex test20b = new Regex("[EuYanSang]");
            Regex test20c = new Regex("[ChineseMedicalHall]");
            Regex test21a = new Regex("[FortCanningPark]");
            Regex test21b = new Regex("[FortPark]");
            Regex test21c = new Regex("[CanningPark]");
            Regex test22a = new Regex("[GardensbytheBay]");
            Regex test22b = new Regex("[BayGardens]");
            Regex test22c = new Regex("[GardensBay]");
            Regex test23a = new Regex("[GeylangSeraiMarket]");
            Regex test23b = new Regex("[GeylangMarket]");
            Regex test23c = new Regex("[SeraiMarket]");
            Regex test24a = new Regex("[G-MaxReverseBungy]");
            Regex test24b = new Regex("[GMax]");
            Regex test24c = new Regex("[Gmax Bungee]");
            Regex test25a = new Regex("[HajjahFatimahMosque]");
            Regex test25b = new Regex("[HajjahMosque]");
            Regex test25c = new Regex("[FatimahMosque]");
            Regex test26a = new Regex("[HawParVilla]");
            Regex test26b = new Regex("[HawPar]");
            Regex test26c = new Regex("[HawParVilla]");
            Regex test27a = new Regex("[HortPark]");
            Regex test27b = new Regex("[ParkHort]");
            Regex test27c = new Regex("[HortPark]");
            Regex test28a = new Regex("[HouseofTanTengNiah]");
            Regex test28b = new Regex("[TanTengNiah]");
            Regex test28c = new Regex("[HouseofTanTengNiah]");
            Regex test29a = new Regex("[ImagesofSingapore]");
            Regex test29b = new Regex("[SingaporeImages]");
            Regex test29c = new Regex("[PicturesofSingapore]");
            Regex test30a = new Regex("[Istana]");
            Regex test30b = new Regex("[IsTana]");
            Regex test30c = new Regex("[PresidentHouse]");
            Regex test31a = new Regex("[JamaeMosque]");
            Regex test31b = new Regex("[Jamae]");
            Regex test31c = new Regex("[JamMosque]");
            Regex test32a = new Regex("[JooChiatStreet]");
            Regex test32b = new Regex("[JooChiat]");
            Regex test32c = new Regex("[JooChiatStreet]");
            Regex test33a = new Regex("[JurongBirdPark]");
            Regex test33b = new Regex("[Jurongpark]");
            Regex test33c = new Regex("[BirdPark]");
            Regex test34a = new Regex("[Katong]");
            Regex test34b = new Regex("[KaTong]");
            Regex test34c = new Regex("[cartong]");
            Regex test35a = new Regex("[KranjiWarMemorial]");
            Regex test35b = new Regex("[WarMemorial]");
            Regex test35c = new Regex("[KranjiMemorial]");
            Regex test36a = new Regex("[KusuIsland]");
            Regex test36b = new Regex("[Kusu]");
            Regex test36c = new Regex("[KusuLand]");
            Regex test37a = new Regex("[KwanImThongHoodChoTemple]");
            Regex test37b = new Regex("[KwanImThong]");
            Regex test37c = new Regex("[HoodChoTemple]");
            Regex test38a = new Regex("[LauPaSat]");
            Regex test38b = new Regex("[TelokAyerMarket]");
            Regex test38c = new Regex("[LauPaSat]");
            Regex test39a = new Regex("[LeongSanSeeTemple]");
            Regex test39b = new Regex("[LeongSanSee]");
            Regex test39c = new Regex("[LeongSeeTemple]");
            Regex test40a = new Regex("[MaghainAbothSynagogue]");
            Regex test40b = new Regex("[MaghainSynagogue]");
            Regex test40c = new Regex("[AbothSynagogue]");
            Regex test41a = new Regex("[MalayHeritageCentre]");
            Regex test41b = new Regex("[MalayCenter]");
            Regex test41c = new Regex("[MalayHeritage]");
            Regex test42a = new Regex("[MalayVillage]");
            Regex test42b = new Regex("[VillageofMalays]");
            Regex test42c = new Regex("[MalaysVillages]");
            Regex test43a = new Regex("[MandaiOrchidGardens]");
            Regex test43b = new Regex("[MandaiGardens]");
            Regex test43c = new Regex("[MandaiOrchid]");
            Regex test44a = new Regex("[MarinaBarrage]");
            Regex test44b = new Regex("[Barrage]");
            Regex test44c = new Regex("[MarinesBarrage]");
            Regex test45a = new Regex("[MarinaBaySandsCasino]");
            Regex test45b = new Regex("[MarinaCasino]");
            Regex test45c = new Regex("[MBSCasino]");
            Regex test46a = new Regex("[MarinaBaySandsSkyPark]");
            Regex test46b = new Regex("[SkyPark]");
            Regex test46c = new Regex("[MBSSkypark]");
            Regex test47a = new Regex("[MaxwellRoadHawkerCentre]");
            Regex test47b = new Regex("[MaxwellRoad]");
            Regex test47c = new Regex("[MaxwellFoodCourt]");
            Regex test48a = new Regex("[MerlionPark]");
            Regex test48b = new Regex("[Merlion]");
            Regex test48c = new Regex("[LionPark]");
            Regex test49a = new Regex("[MountFaberPark]");
            Regex test49b = new Regex("[MountPark]");
            Regex test49c = new Regex("[MountFaber]");
            Regex test50a = new Regex("[NagoreDurghaShrine]");
            Regex test50b = new Regex("[NagoreShrine]");
            Regex test50c = new Regex("[DurghaShrine]");
            Regex test51a = new Regex("[NationalMuseumofSingapore]");
            Regex test51b = new Regex("[NationalMuseum]");
            Regex test51c = new Regex("[MuseumofSingapore]");
            Regex test52a = new Regex("[NationalOrchidGarden]");
            Regex test52b = new Regex("[OrchidGarden]");
            Regex test52c = new Regex("[NationalOrchid]");
            Regex test53a = new Regex("[NEWaterVisitorCentre]");
            Regex test53b = new Regex("[NEWater]");
            Regex test53c = new Regex("[Newwatervisitorcentre]");
            Regex test54a = new Regex("[OldParliamentHouse]");
            Regex test54b = new Regex("[ParliamentHouse]");
            Regex test54c = new Regex("[OldParliament]");
            Regex test55a = new Regex("[PeranakanMuseum]");
            Regex test55b = new Regex("[Peranakan]");
            Regex test55c = new Regex("[peranakanMuseum]");
            Regex test56a = new Regex("[PulauUbin]");
            Regex test56b = new Regex("[Ubin]");
            Regex test56c = new Regex("[PulauUbin]");
            Regex test57a = new Regex("[RafflesHotel]");
            Regex test57b = new Regex("[HotelRaffles]");
            Regex test57c = new Regex("[Raffles Hotel]");
            Regex test58a = new Regex("[RafflesPlace]");
            Regex test58b = new Regex("[PlaceRaffles]");
            Regex test58c = new Regex("[Raffles Place]");
            Regex test59a = new Regex("[RedDotDesignMuseum]");
            Regex test59b = new Regex("[RedDot]");
            Regex test59c = new Regex("[DesignMuseum]");
            Regex test60a = new Regex("[ResortWorldSentosaCasino]");
            Regex test60b = new Regex("[RWSCasino]");
            Regex test60c = new Regex("[SentosaCasino]");
            Regex test61a = new Regex("[SentosaSkylineLugeandSkyride]");
            Regex test61b = new Regex("[SentosaLuge]");
            Regex test61c = new Regex("[SentosaSkyride]");
            Regex test62a = new Regex("[SingaporeArtMuseum]");
            Regex test62b = new Regex("[ArtMuseum]");
            Regex test62c = new Regex("[SingaporeArt]");
            Regex test63a = new Regex("[SingaporeBazaarandFleaMarket]");
            Regex test63b = new Regex("[SingaporeBazaar]");
            Regex test63c = new Regex("[SingaporeFleaMarket]");
            Regex test64a = new Regex("[SingaporeBotanicGardens]");
            Regex test64b = new Regex("[BotanicGardens]");
            Regex test64c = new Regex("[SingaporeGardens]");
            Regex test65a = new Regex("[SingaporeButterflyandInsectKingdom]");
            Regex test65b = new Regex("[InsectKingdom]");
            Regex test65c = new Regex("[ButterflyKingdom]");
            Regex test66a = new Regex("[SingaporeDiscoveryCentre]");
            Regex test66b = new Regex("[DiscoveryCentre]");
            Regex test66c = new Regex("[SingaporeDiscovery]");
            Regex test67a = new Regex("[SingaporeF1GrandPrix]");
            Regex test67b = new Regex("[SingaporeF1]");
            Regex test67c = new Regex("[F1]");
            Regex test68a = new Regex("[SingaporeFlyer]");
            Regex test68b = new Regex("[Flyer]");
            Regex test68c = new Regex("[FlyerSingapore]");
            Regex test69a = new Regex("[SingaporeMintCoinGallery]");
            Regex test69b = new Regex("[SingaporeMintGallery]");
            Regex test69c = new Regex("[SingaporeCoinGallery]");
            Regex test70a = new Regex("[SingaporeNavyMuseum]");
            Regex test70b = new Regex("[NavyMuseum]");
            Regex test70c = new Regex("[Singaporenavy]");
            Regex test71a = new Regex("[SingaporeNightSafari]");
            Regex test71b = new Regex("[NightSafari]");
            Regex test71c = new Regex("[SingaporeSafari]");
            Regex test72a = new Regex("[SingaporePhilatelicMuseum]");
            Regex test72b = new Regex("[SingaporePhilatelic]");
            Regex test72c = new Regex("[PhilatelicMuseum]");
            Regex test73a = new Regex("[SingaporeRiver]");
            Regex test73b = new Regex("[RiverofSingapore]");
            Regex test73c = new Regex("[River]");
            Regex test74a = new Regex("[ScienceCentreSingapore]");
            Regex test74b = new Regex("[ScienceCentre]");
            Regex test74c = new Regex("[SingaporeScienceCentre]");
            Regex test75a = new Regex("[SingaporeZoo]");
            Regex test75b = new Regex("[Zoo]");
            Regex test75c = new Regex("[ZooofSingapore]");
            Regex test76a = new Regex("[SiongLimTemple]");
            Regex test76b = new Regex("[SiongTemple]");
            Regex test76c = new Regex("[SionglimTemple]");
            Regex test77a = new Regex("[SistersIslands]");
            Regex test77b = new Regex("[SistersIslands]");
            Regex test77c = new Regex("[Sistersislands]");
            Regex test78a = new Regex("[Songsofthesea]");
            Regex test78b = new Regex("[SeaSongs]");
            Regex test78c = new Regex("[SongsSea]");
            Regex test79a = new Regex("[SriKrishnanTemple]");
            Regex test79b = new Regex("[KrishnanTemple]");
            Regex test79c = new Regex("[SriKrishnanTemple]");
            Regex test80a = new Regex("[SriMariammanTemple]");
            Regex test80b = new Regex("[MariammanTample]");
            Regex test80c = new Regex("[SriMariammanTemple]");
            Regex test81a = new Regex("[SriSrinivasaPerumalTemple]");
            Regex test81b = new Regex("[SrinivasaTemple]");
            Regex test81c = new Regex("[PermualTemple]");
            Regex test82a = new Regex("[SriThandayuthapaniTemple]");
            Regex test82b = new Regex("[ThandayuthapaniTemple]");
            Regex test82c = new Regex("[SriThandayuthapaniTemple]");
            Regex test83a = new Regex("[SriVeeramakalisammanTemple]");
            Regex test83b = new Regex("[VeeramakalisammanTemple]");
            Regex test83c = new Regex("[SriVeeramakalisammanTemple]");
            Regex test84a = new Regex("[StAndrewsCathedral]");
            Regex test84b = new Regex("[StAndrew]");
            Regex test84c = new Regex("[SaintAndrewCathedral]");
            Regex test85a = new Regex("[StJohnsIsland]");
            Regex test85b = new Regex("[StJohn]");
            Regex test85c = new Regex("[SaintJohnIsland]");
            Regex test86a = new Regex("[StatuesofSirStamfordRaffles]");
            Regex test86b = new Regex("[Statue]");
            Regex test86c = new Regex("[SirStamfordRaffles]");
            Regex test87a = new Regex("[SultanMosque]");
            Regex test87b = new Regex("[MosqueSultan]");
            Regex test87c = new Regex("[SultansMosque]");
            Regex test88a = new Regex("[SungeiBulohNaturePark]");
            Regex test88b = new Regex("[SungeiBuloh]");
            Regex test88c = new Regex("[SungeiNaturePark]");
            Regex test89a = new Regex("[SupremeCourtandCityHall]");
            Regex test89b = new Regex("[SupremeCourt]");
            Regex test89c = new Regex("[CityHall]");
            Regex test90a = new Regex("[Templeof1000Lights]");
            Regex test90b = new Regex("[SakyamuniBuddhaGayaTemple]");
            Regex test90c = new Regex("[TempleofonethousandLights]");
            Regex test91a = new Regex("[ThePadang]");
            Regex test91b = new Regex("[Padang]");
            Regex test91c = new Regex("[Pandan]");
            Regex test92a = new Regex("[TheSouthernRidges]");
            Regex test92b = new Regex("[SouthernRidges]");
            Regex test92c = new Regex("[Ridges]");
            Regex test93a = new Regex("[ThianHockKengTemple]");
            Regex test93b = new Regex("[ThianHockKeng]");
            Regex test93c = new Regex("[HockKengTemple]");
            Regex test94a = new Regex("[TreetopTalkatMacRitchieReservoir]");
            Regex test94b = new Regex("[TreetopWalk]");
            Regex test94c = new Regex("[MacRitchieReservoir]");
            Regex test95a = new Regex("[UnderwaterWorld]");
            Regex test95b = new Regex("[UnderWaterWorld]");
            Regex test95c = new Regex("[underwaterworld]");
            Regex test96a = new Regex("[UniversalStudiosSingapore]");
            Regex test96b = new Regex("[UniversalStudios]");
            Regex test96c = new Regex("[USSingapore]");
            Regex test97a = new Regex("[WonderFullatMarinaBaySands]");
            Regex test97b = new Regex("[Wonderful]");
            Regex test97c = new Regex("[WonderFull]");
            Regex test98a = new Regex("[marinabaysands]");
            Regex test98b = new Regex("[mbs]");
            Regex test98c = new Regex("[marinasands]");
            Regex test99a = new Regex("[vivocity]");
            Regex test99b = new Regex("[vivo city]");
            Regex test99c = new Regex("[vivo's city]");
            Regex test100a = new Regex("[Resortsworldsentosa]");
            Regex test100b = new Regex("[sentosa]");
            Regex test100c = new Regex("[sentosabeach]");
            if (a.equals("exit")) {
                running = false;
            }
            if (test1a.match(a)|test1b.match(a)|test1c.match(a)|a.equals("AGM")) {
                System.out.println("matched Abdul Gaffoor Mosque");
            } else if (test2a.match(a)|test2b.match(a)|test2c.match(a)|a.equals("AAM")) {
                System.out.println("matched Al-Abrar Mosque");
            } else if (test3a.match(a)|test3b.match(a)|test3c.match(a)|a.equals("ASM")) {
                System.out.println("matched ArtScience Museum");
            }else if (test4a.match(a)|test4b.match(a)|test4c.match(a)|a.equals("ACM")) {
                System.out.println("matched Asian Civilisations Museum");
            }else if (test5a.match(a)|test5b.match(a)|test5c.match(a)|a.equals("BHT")|a.equals("KMSPKST")) {
                System.out.println("matched Bright Hill Temple (Khong Meng San Phor Kark See Temple)");
            }else if (test6a.match(a)|test6b.match(a)|test6c.match(a)|a.equals("BTRT")) {
                System.out.println("matched Buddha Tooth Relic Temple");
            }else if (test7a.match(a)|test7b.match(a)|test7c.match(a)|a.equals("BTNT")) {
                System.out.println("matched Bukit Timah Nature Reserve");
            }else if (test8a.match(a)|test8b.match(a)|test8c.match(a)|a.equals("CGS")) {
                System.out.println("matched Cathedral of the Good Shepherd");
            }else if (test9a.match(a)|test9b.match(a)|test9c.match(a)|a.equals("CCNR")) {
                System.out.println("matched Central Catchment Nature Reserve");
            }else if (test10a.match(a)|test10b.match(a)|test10c.match(a)|a.equals("CST")) {
                System.out.println("matched Central Sikh Temple");
            }else if (test11a.match(a)|test11b.match(a)|test11c.match(a)|a.equals("CPCM")) {
                System.out.println("matched Changi Prison Chapel and Museum");
            }else if (test12a.match(a)|test12b.match(a)|test12c.match(a)|a.equals("C")) {
                System.out.println("matched Chjimes");
            }else if (test13a.match(a)|test13b.match(a)|test13c.match(a)|a.equals("CHC")) {
                System.out.println("matched Chinatown Heritage Centre");
            }else if (test14a.match(a)|test14b.match(a)|test14c.match(a)|a.equals("CJG")) {
                System.out.println("matched Chinese and Japanese Gardens");
            }else if (test15a.match(a)|test15b.match(a)|test15c.match(a)|a.equals("CMC")) {
                System.out.println("matched Chinese Methodist Church");
            }else if (test16a.match(a)|test16b.match(a)|test16c.match(a)|a.equals("CSGI")) {
                System.out.println("matched Church of St Gregory the Illuminator");
            }else if (test17a.match(a)|test17b.match(a)|test17c.match(a)|a.equals("CD")) {
                System.out.println("matched Crane Dance");
            }else if (test18a.match(a)|test18b.match(a)|test18c.match(a)|a.equals("ECP")) {
                System.out.println("matched East Coast Park");
            }else if (test19a.match(a)|test19b.match(a)|test19c.match(a)|a.equals("ETB")) {
                System.out.println("matched Esplanade Theatres on the Bay");
            }else if (test20a.match(a)|test20b.match(a)|test20c.match(a)|a.equals("EYSCMH")) {
                System.out.println("matched Eu Yan Sang Chinese Medical Hall");
            }else if (test21a.match(a)|test21b.match(a)|test21c.match(a)|a.equals("FCP")) {
                System.out.println("matched Fort Canning Park");
            }else if (test22a.match(a)|test22b.match(a)|test22c.match(a)|a.equals("GBB")) {
                System.out.println("matched Gardens By the Bay");
            }else if (test23a.match(a)|test23b.match(a)|test23c.match(a)|a.equals("GSM")) {
                System.out.println("matched Geylang Serai Market");
            }else if (test24a.match(a)|test24b.match(a)|test24c.match(a)|a.equals("GRB")) {
                System.out.println("matched G-Max Reverse Bungy");
            }else if (test25a.match(a)|test25b.match(a)|test25c.match(a)|a.equals("HFM")) {
                System.out.println("matched Hajjah Fatimah Mosque");
            }else if (test26a.match(a)|test26b.match(a)|test26c.match(a)|a.equals("HPV")) {
                System.out.println("matched Haw Par Villa");
            }else if (test27a.match(a)|test27b.match(a)|test27c.match(a)|a.equals("HP")) {
                System.out.println("matched HortPark");
            }else if (test28a.match(a)|test28b.match(a)|test28c.match(a)|a.equals("HTTN")) {
                System.out.println("matched House of Tan Teng Niah");
            }else if (test29a.match(a)|test29b.match(a)|test29c.match(a)|a.equals("IS")) {
                System.out.println("matched Images of Singapore");
            }else if (test30a.match(a)|test30b.match(a)|test30c.match(a)|a.equals("I")) {
                System.out.println("matched Istana");
            }else if (test31a.match(a)|test31b.match(a)|test31c.match(a)|a.equals("JM")) {
                System.out.println("matched Jamae Mosque");
            }else if (test32a.match(a)|test32b.match(a)|test32c.match(a)|a.equals("JCS")) {
                System.out.println("matched Joo Chiat Street");
            }else if (test33a.match(a)|test33b.match(a)|test33c.match(a)|a.equals("JBP")) {
                System.out.println("matched Jurong Bird Park");
            }else if (test34a.match(a)|test34b.match(a)|test34c.match(a)|a.equals("K")) {
                System.out.println("matched Katong");
            }else if (test35a.match(a)|test35b.match(a)|test35c.match(a)|a.equals("KWM")) {
                System.out.println("matched Kranji War Memorial");
            }else if (test36a.match(a)|test36b.match(a)|test36c.match(a)|a.equals("KI")) {
                System.out.println("matched Kusu Island");
            }else if (test37a.match(a)|test37b.match(a)|test37c.match(a)|a.equals("KITHCT")) {
                System.out.println("matched Kwan Im Thong Hood Cho Temple");
            }else if (test38a.match(a)|test38b.match(a)|test38c.match(a)|a.equals("TAM")) {
                System.out.println("matched Lau Pa Sat (Telok Ayer Market)");
            }else if (test39a.match(a)|test39b.match(a)|test39c.match(a)|a.equals("LSST")) {
                System.out.println("matched Leong San See Temple");
            }else if (test40a.match(a)|test40b.match(a)|test40c.match(a)|a.equals("MAS")) {
                System.out.println("matched Maghain Aboth Synagogue");
            }else if (test41a.match(a)|test41b.match(a)|test41c.match(a)|a.equals("MHC")) {
                System.out.println("matched Malay Heritage Centre");
            }else if (test42a.match(a)|test42b.match(a)|test42c.match(a)|a.equals("MV")) {
                System.out.println("matched Malay Village");
            }else if (test43a.match(a)|test43b.match(a)|test43c.match(a)|a.equals("MOG")) {
                System.out.println("matched Mandai Orchid Gardens");
            }else if (test44a.match(a)|test44b.match(a)|test44c.match(a)|a.equals("MB")) {
                System.out.println("matched Marina Barrage");
            }else if (test45a.match(a)|test45b.match(a)|test45c.match(a)|a.equals("MBSC")) {
                System.out.println("matched Marina Bay Sands Casino");
            }else if (test46a.match(a)|test46b.match(a)|test46c.match(a)|a.equals("MBSC")) {
                System.out.println("matched Marina Bay Sands Skypark");
            }else if (test47a.match(a)|test47b.match(a)|test47c.match(a)|a.equals("MRHC")) {
                System.out.println("matched Maxwell Road Hawker Centre");
            }else if (test48a.match(a)|test48b.match(a)|test48c.match(a)|a.equals("MP")) {
                System.out.println("matched Merlion Park");
            }else if (test49a.match(a)|test49b.match(a)|test49c.match(a)|a.equals("MFP")) {
                System.out.println("matched Mount Faber Park");
            }else if (test50a.match(a)|test50b.match(a)|test50c.match(a)|a.equals("NDS")) {
                System.out.println("matched Nagore Durgha Shrine");
            }else if (test51a.match(a)|test51b.match(a)|test51c.match(a)|a.equals("NMS")) {
                System.out.println("matched National Museum of Singapore");
            }else if (test52a.match(a)|test52b.match(a)|test52c.match(a)|a.equals("NOG")) {
                System.out.println("matched National Orchid Garden");
            }else if (test53a.match(a)|test53b.match(a)|test53c.match(a)|a.equals("NVC")) {
                System.out.println("matched NEWater Visitor Centre");
            }else if (test54a.match(a)|test54b.match(a)|test54c.match(a)|a.equals("OPH")) {
                System.out.println("matched Old Parliament House");
            }else if (test55a.match(a)|test55b.match(a)|test55c.match(a)|a.equals("PM")) {
                System.out.println("matched Peranakan Museum");
            }else if (test56a.match(a)|test56b.match(a)|test56c.match(a)|a.equals("PU")) {
                System.out.println("matched Pulau Ubin");
            }else if (test57a.match(a)|test57b.match(a)|test57c.match(a)|a.equals("RH")) {
                System.out.println("matched Raffles Hotel");
            }else if (test58a.match(a)|test58b.match(a)|test58c.match(a)|a.equals("RP")) {
                System.out.println("matched Raffles Place");
            }else if (test59a.match(a)|test59b.match(a)|test59c.match(a)|a.equals("RDDM")) {
                System.out.println("matched Red Dot Design Museum");
            }else if (test60a.match(a)|test60b.match(a)|test60c.match(a)|a.equals("RWSC")) {
                System.out.println("matched Resort World Sentosa Casino");
            }else if (test61a.match(a)|test61b.match(a)|test61c.match(a)|a.equals("SSLS")) {
                System.out.println("matched Sentosa Skyline Luge and Skyride");
            }else if (test62a.match(a)|test62b.match(a)|test62c.match(a)|a.equals("SAM")) {
                System.out.println("matched Singapore Art Museum");
            }else if (test63a.match(a)|test63b.match(a)|test63c.match(a)|a.equals("SBFM")) {
                System.out.println("matched Singapore Bazaar and Flea Market");
            }else if (test64a.match(a)|test64b.match(a)|test64c.match(a)|a.equals("SBG")) {
                System.out.println("matched Singapore Botanic Gardens");
            }else if (test65a.match(a)|test65b.match(a)|test65c.match(a)|a.equals("SBIK")) {
                System.out.println("matched Singapore Buttergly and Insect Kingdom");
            }else if (test66a.match(a)|test66b.match(a)|test66c.match(a)|a.equals("SDC")) {
                System.out.println("matched Singapore Discovery Centre");
            }else if (test67a.match(a)|test67b.match(a)|test67c.match(a)|a.equals("SFGP")) {
                System.out.println("matched Singapore F1 Grand Prix");
            }else if (test68a.match(a)|test68b.match(a)|test68c.match(a)|a.equals("SF")) {
                System.out.println("matched Singapore Flyer");
            }else if (test69a.match(a)|test69b.match(a)|test69c.match(a)|a.equals("SMCG")) {
                System.out.println("matched Singapore Mint Coin Gallery");
            }else if (test70a.match(a)|test70b.match(a)|test70c.match(a)|a.equals("SNM")) {
                System.out.println("matched Singapore Navy Museum");
            }else if (test71a.match(a)|test71b.match(a)|test71c.match(a)|a.equals("SNS")) {
                System.out.println("matched Singapore Night Safari");
            }else if (test72a.match(a)|test72b.match(a)|test72c.match(a)|a.equals("SPM")) {
                System.out.println("matched Singapore Philatelic Museum");
            }else if (test73a.match(a)|test73b.match(a)|test73c.match(a)|a.equals("SR")) {
                System.out.println("matched Singapore River");
            }else if (test74a.match(a)|test74b.match(a)|test74c.match(a)|a.equals("SCS")) {
                System.out.println("matched Science Centre Singapore");
            }else if (test75a.match(a)|test75b.match(a)|test75c.match(a)|a.equals("SZ")) {
                System.out.println("matched Singapore Zoo");
            }else if (test76a.match(a)|test76b.match(a)|test76c.match(a)|a.equals("SLT")) {
                System.out.println("matched Siong Lim Temple");
            }else if (test77a.match(a)|test77b.match(a)|test77c.match(a)|a.equals("SI")) {
                System.out.println("matched Sisters Islands");
            }else if (test78a.match(a)|test78b.match(a)|test78c.match(a)|a.equals("SS")) {
                System.out.println("matched Songs of the Sea");
            }else if (test79a.match(a)|test79b.match(a)|test79c.match(a)|a.equals("SKT")) {
                System.out.println("matched Sri Krishnan Temple");
            }else if (test80a.match(a)|test80b.match(a)|test80c.match(a)|a.equals("SMT")) {
                System.out.println("matched Sri Mariamman Temple");
            }else if (test81a.match(a)|test81b.match(a)|test81c.match(a)|a.equals("SSPT")) {
                System.out.println("matched Sri Srinivasa Perumal Temple");
            }else if (test82a.match(a)|test82b.match(a)|test82c.match(a)|a.equals("STT")) {
                System.out.println("matched Sri Thandayuthapani Temple");
            }else if (test83a.match(a)|test83b.match(a)|test83c.match(a)|a.equals("SVT")) {
                System.out.println("matched Sri Veeramakaliamman Temple");
            }else if (test84a.match(a)|test84b.match(a)|test84c.match(a)|a.equals("SAC")) {
                System.out.println("matched St Andrews Cathedral");
            }else if (test85a.match(a)|test85b.match(a)|test85c.match(a)|a.equals("SJI")) {
                System.out.println("matched St Johns Island");
            }else if (test86a.match(a)|test86b.match(a)|test86c.match(a)|a.equals("SSR")) {
                System.out.println("matched Statues of Sir Stamford Raffles");
            }else if (test87a.match(a)|test87b.match(a)|test87c.match(a)|a.equals("SM")) {
                System.out.println("matched Sultan Mosque");
            }else if (test88a.match(a)|test88b.match(a)|test88c.match(a)|a.equals("SBNP")) {
                System.out.println("matched Sungei Buloh Nature Park");
            }else if (test89a.match(a)|test89b.match(a)|test89c.match(a)|a.equals("SCCH")) {
                System.out.println("matched Supreme Court & City Hall");
            }else if (test90a.match(a)|test90b.match(a)|test90c.match(a)|a.equals("TLSBGT")) {
                System.out.println("matched Temple of 1,000 Lights (Sakyamuni Buddha Gaya Temple)");
            }else if (test91a.match(a)|test91b.match(a)|test91c.match(a)|a.equals("TP")) {
                System.out.println("matched The Padang");
            }else if (test92a.match(a)|test92b.match(a)|test92c.match(a)|a.equals("TSR")) {
                System.out.println("matched The Southern Ridges");
            }else if (test93a.match(a)|test93b.match(a)|test93c.match(a)|a.equals("THKT")) {
                System.out.println("matched Thian Hock Keng Temple");
            }else if (test94a.match(a)|test94b.match(a)|test94c.match(a)|a.equals("TWMR")) {
                System.out.println("matched Treetop Walk at MacRitchie Reservoir");
            }else if (test95a.match(a)|test95b.match(a)|test95c.match(a)|a.equals("UW")) {
                System.out.println("matched Underwater World");
            }else if (test96a.match(a)|test96b.match(a)|test96c.match(a)|a.equals("USS")) {
                System.out.println("matched Universal Studios Singapore");
            }else if (test97a.match(a)|test97b.match(a)|test97c.match(a)|a.equals("WFMBS")) {
                System.out.println("matched Wonder Full at Marina Bay Sands");
            }else if (test98a.match(a)|test98b.match(a)|test98c.match(a)|a.equals("MBS")) {
                System.out.println("matched Marina Bay Sands");
            }else if (test99a.match(a)|test99b.match(a)|test99c.match(a)|a.equals("VC")) {
                System.out.println("matched VivoCity");
            }else if (test100a.match(a)|test100b.match(a)|test100c.match(a)|a.equals("RWS")) {
                System.out.println("matched Resorts World Sentosa");
            } else if (a.equals("exit")) {
                System.out.println("bye!");
            }else {
                System.out.println("no match");
            }
        }
    }
}
