package fr.gms.navigation.gnss.protocols.nmea.trames.v3.readers;

import fr.gms.navigation.gnss.protocols.nmea.Nmea.TrameType;
import fr.gms.navigation.gnss.protocols.nmea.NmeaCodec;

public class _Readers {

	public static final void registerReaders() {
		register_GT3731_Readers();
	}

	public static final void register_GT3731_Readers() {
		NmeaCodec.registerTrameReader(TrameType.GGA, GGATrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GLL, GLLTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GSA, GSATrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GSV, GSVTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.RMC, RMCTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.VTG, VTGTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.ZDA, ZDATrameReader::new);
	}

	public static final void register_ALL_Readers() {
		NmeaCodec.registerTrameReader(TrameType.APB, APBTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.BOD, BODTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.CUR, CURTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.DBT, DBTTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.DPT, DPTTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.DTM, DTMTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GBS, GBSTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GGA, GGATrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GLL, GLLTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GNS, GNSTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GSA, GSATrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GST, GSTTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.GSV, GSVTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.HDG, HDGTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.HDT, HDTTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.HTC, HTCTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.HTD, HTDTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.MTA, MTATrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.MTW, MTWTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.MWD, MWDTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.MWV, MWVTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.RMB, RMBTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.RMC, RMCTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.ROT, ROTTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.RPM, RPMTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.RSA, RSATrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.RTE, RTETrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.TTM, TTMTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.VBW, VBWTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.VDR, VDRTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.VHW, VHWTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.VLW, VLWTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.VTG, VTGTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.WPL, WPLTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.XDR, XDRTrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.XTE, XTETrameReader::new);
		NmeaCodec.registerTrameReader(TrameType.ZDA, ZDATrameReader::new);
	}

}
