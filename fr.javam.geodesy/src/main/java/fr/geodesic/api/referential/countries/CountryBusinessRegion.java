package fr.geodesic.api.referential.countries;

import static fr.geodesic.api.referential.countries.Country.*;

import java.util.HashSet;
import java.util.Set;

public enum CountryBusinessRegion implements CountryRegion {
    AMERICAS		(AI, AG, AR, AW, BS, BB, BZ, BM, BO, BR, CA, KY, CL, CO, CR, CU, DM, DO, EC, SV, GF, GD, GP, GT, GY, HT, HN, JM, MQ, MX, MS, NI, PA, PY, PE, PR, BL, KN, LC, MF, PM, VC, SR, TT, TC, US, UY, VE, VG, VI),
    APAC			(AS, AU, BD, BN, BT, CC, CK, CN, CX, FJ, FM, GU, HK, ID, IN, IO, JP, KH, KI, KP, KR, LA, LK, MH, MM, MN, MO, MP, MV, MY, NC, NF, NP, NR, NU, NZ, PF, PG, PH, PK, PN, PW, SB, SG, TH, TK, TL, TO, TV, TW, VN, VU, WF, WS),
    APJC			(AS, AU, BD, BN, BT, CC, CK, CN, CX, FJ, FM, GU, HK, HM, ID, IN, IO, JP, KH, KI, KP, KR, LA, LK, MH, MM, MN, MO, MP, MV, MY, NC, NF, NP, NR, NU, NZ, PF, PG, PH, PN, PW, SB, SG, TH, TK, TL, TO, TV, TW, VN, VU, WS),
    ANZ				(AU, NZ),
    BENELUX			(BE, NL, LU),
    BRICS			(RU, BR, CN, IN, ZA),
    DACH			(DE, AT, CH),
    EMEA			(AF, AX, AL, DZ, AD, AO, AM, AT, AZ, BH, BY, BE, BJ, BA, BW, BV, BG, BF, BI, CM, CV, CF, TD, KM, CD, CG, HR, CY, CZ, DK, DJ, EG, GQ, ER, EE, ET, FK, FO, FI, FR, GA, GM, GE, DE, GH, GI, GR, GL, GG, GW, HU, IS, IR, IQ, IE, IM, IL, IT, CI, JE, JO, KZ, KE, KV, KW, KG, LV, LB, LS, LR, LY, LI, LT, LU, MK, MG, MW, ML, MT, MR, MU, YT, MD, MC, ME, MA, MZ, NA, NL, NE, NG, NO, OM, PK, PS, PL, PT, QA, RE, RO, RU, RW, SH, SM, ST, SA, SN, RS, SC, SL, SK, SI, SO, ZA, GS, ES, SD, SJ, SZ, SE, CH, SY, TJ, TZ, TG, TN, TR, TM, UG, UA, AE, GB, UZ, VA, EH, YE, ZM, ZW),
    EU				(BE, GR, LT, PT, BG, ES, LU, RO, CZ, FR, HU, SI, DK, HR, MT, SK, DE, IT, NL, FI, EE, CY, AT, SE, IE, LV, PL, GB),
    NORAM			(US, CA, MX, GT, BZ, CU, DO, HT, HN, SV, NI, CR, PA);

    private Set<Country> countries;

    CountryBusinessRegion(final Country... COUNTRIES) {
        countries = new HashSet<Country>(COUNTRIES.length);
        for(Country country : COUNTRIES) { countries.add(country); }
    }

    @Override 
    public Set<Country> getCountries() { return countries; }

}
