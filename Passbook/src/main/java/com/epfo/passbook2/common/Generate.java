/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import com.epfo.passbook2.model.MemberData;
import com.epfo.passbook2.model.PassbookTransaction;
import com.epfo.passbook2.model.ServiceData;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class Generate {

    public static class Member {

        public static ArrayList<MemberData> GetMembers(int size) {

            ArrayList<MemberData> mems = new ArrayList();
            if (size < 0 || size > 10) {
                return mems;
            }

            for (int i = 0; i <= size; i++) {

                String mid = GenMemberId();

                MemberData ml = new MemberData();
                ml.setMemberId(mid);
                ml.setSelected(Boolean.TRUE);
                mems.add(ml);
                //mems.add(String.valueOf(i));
            }

            return mems;

        }

        private static String GenMemberId() {

            String[] office = {"APKKP",
                "APPTC",
                "APSID",
                "BGBNG",
                "BGMRD",
                "BRBHA",
                "BRMUZ",
                "BRPAT",
                "CBCBE",
                "CBSLM",
                "CBTRY",
                "CGRAI",
                "DLCPM",
                "DSNHP",
                "DSSHD",
                "GAGOA",
                "GBBLR",
                "GBGLB",
                "GBHBL",
                "GBRCH",
                "GJAHD",
                "GJNRD",
                "GJRAJ",
                "GJVAT",
                "GNGGN",
                "GNRTK",
                "GRCDP",
                "GRGNT",
                "GRRJY",
                "GRVSP",
                "HPSML",
                "HRFBD",
                "HRKNL",
                "JHJAM",
                "JHRAN",
                "JKJMU",
                "JKSRN",
                "JLDRJ",
                "JLJLP",
                "JLJNG",
                "JLSLG",
                "KDMAL",
                "KDNSK",
                "KNCKR",
                "KNMLR",
                "KNMYS",
                "KNSHG",
                "KNUDP",
                "KRKCH",
                "KRKKD",
                "KRKLM",
                "KRKNR",
                "KRKTM",
                "KRTVM",
                "LDASR",
                "LDJAL",
                "LDLDH",
                "LKLEH",
                "MDMDU",
                "MDNKL",
                "MDTNY",
                "MHBAN",
                "MPBPL",
                "MPGWL",
                "MPIND",
                "MPJBP",
                "MPSGR",
                "MPUJJ",
                "MRAGR",
                "MRMRT",
                "MRNOI",
                "NEAGT",
                "NEGHY",
                "NESHG",
                "NETSK",
                "NGAKL",
                "NGAUR",
                "NGNAG",
                "NZKRN",
                "NZNZB",
                "NZWGL",
                "ORBAM",
                "ORBBS",
                "ORKJR",
                "ORRKL",
                "PBBTI",
                "PBCHD",
                "PUKOL",
                "PUPUN",
                "PUSLP",
                "PYBOM",
                "PYKRP",
                "PYPNY",
                "RJJOD",
                "RJKOT",
                "RJRAJ",
                "RJUDR",
                "SRBRH",
                "SRSRT",
                "SRVAP",
                "TBPDY",
                "TBTAM",
                "TBVLR",
                "THTHA",
                "THVSH",
                "TNAMB",
                "TNMAS",
                "UKDDN",
                "UKHLD",
                "UPALD",
                "UPBLY",
                "UPGKP",
                "UPKNP",
                "UPLKO",
                "UPVNS",
                "VDBRD",
                "WBAND",
                "WBCAL",
                "WBDGP",
                "WBHLO",
                "WBPRB",
                "WBTLO",};

            Random rnd = new Random();

            int ofc = rnd.nextInt(122);

            String ofcCode = office[ofc];

            rnd = new Random();

            int est = rnd.nextInt(99999);

            int mid = rnd.nextInt(99999);

            String memId = ofcCode + lpad(String.valueOf(est), 7) + "000" + lpad(String.valueOf(mid), 7);

            //System.out.print(memId);
            return memId;
        }

        private static String lpad(String inputString, int length) {
            if (inputString.length() >= length) {
                return inputString;
            }
            StringBuilder sb = new StringBuilder();
            while (sb.length() < length - inputString.length()) {
                sb.append('0');
            }
            sb.append(inputString);

            return sb.toString();
        }
    }

    public static class Service {

        public static ArrayList<MemberData> genService(ArrayList<MemberData> memList) {

            int sr = 1;
            for (MemberData md : memList) {

                ArrayList<MemberData.ServiceData> serList = new ArrayList();

                int sl = createRandomIntBetween(1, 2);

                for (int i = 1; i <= sl; i++) {
                    MemberData.ServiceData sd = new MemberData.ServiceData();
                    sd.setSr(sr);
                    sd.setMemberId(md.getMemberId());
                    sd.setEstId(md.getMemberId().substring(1, 15));
                    sd.setEstName("Some Est Name");
                    LocalDate doj = getDOJ(createRandomIntBetween(2010, 2018));
                    int ser = createRandomIntBetween(0, 1);
                    LocalDate doe;
                    if (ser == 1) {
                        doe = getNextDate(doj);
                        sd.setDoe(Date.from(doe.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    }

                    sd.setDoj(Date.from(doj.atStartOfDay(ZoneId.systemDefault()).toInstant()));

                    serList.add(sd);
                }

                md.setServiceList(serList);
                sr++;
            }

            return memList;
        }

        public static LocalDate getDOJ(int startYear) {
            int day = createRandomIntBetween(1, 28);
            int month = createRandomIntBetween(1, 12);
            int gap = LocalDate.now().getYear() - startYear;
            int gap1 = createRandomIntBetween(0, gap);
            int year = startYear + gap1;

            return LocalDate.of(year, month, day);
        }

        public static LocalDate getNextDate(LocalDate doj) {
            LocalDate d1 = doj.plusDays(createRandomIntBetween(1, 50));
            LocalDate endDate = doj.plusMonths(createRandomIntBetween(2, 60));

            long startEpochDay = d1.toEpochDay();
            long endEpochDay = endDate.toEpochDay();
            long randomDay = ThreadLocalRandom
                    .current()
                    .nextLong(startEpochDay, endEpochDay);

            return LocalDate.ofEpochDay(randomDay);

        }

        private static int createRandomIntBetween(int start, int end) {
            return start + (int) Math.round(Math.random() * (end - start));
        }

    }

    public static class Passbook {

        public static ArrayList<PassbookTransaction> GetPassbook(String memberId, Date doj, Date doe) {
            ArrayList<PassbookTransaction> passList = new ArrayList();

            return passList;
        }

    }
}
