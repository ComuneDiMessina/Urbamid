package it.eng.tz.urbamid.prg.service;

import java.io.File;

import org.springframework.stereotype.Service;

import it.eng.tz.urbamid.prg.web.dto.InterrogazionePianoDTO;

@Service
public interface InterrogazionePianoService {

	File downloadCdu(InterrogazionePianoDTO dto) throws Exception;

}
