package it.eng.tz.urbamid.prg.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.prg.web.dto.InterrogazionePianoDTO;

@Service
public class InterrogazionePianoServiceImpl implements InterrogazionePianoService {

	private static final Logger logger = LoggerFactory.getLogger(InterrogazionePianoServiceImpl.class.getName());

	private static final String START = "START >>> {}";
	private static final String END = "END >>> {}";
	private static final String DEBUG_INFO_END = "{} >>> numero di risultati estratti: {}";

	@Override
	public File downloadCdu(InterrogazionePianoDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
