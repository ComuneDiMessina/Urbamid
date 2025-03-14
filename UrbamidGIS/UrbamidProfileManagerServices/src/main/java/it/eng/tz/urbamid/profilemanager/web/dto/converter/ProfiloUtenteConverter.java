package it.eng.tz.urbamid.profilemanager.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import it.eng.tz.urbamid.profilemanager.persistence.model.Permesso;
import it.eng.tz.urbamid.profilemanager.persistence.model.Ruolo;
import it.eng.tz.urbamid.profilemanager.persistence.model.Utente;
import it.eng.tz.urbamid.profilemanager.web.dto.PermessoUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.ProfiloUtenteDto;
import it.eng.tz.urbamid.profilemanager.web.dto.RuoloUtenteDto;

@Component
public class ProfiloUtenteConverter implements IConverter<Utente, ProfiloUtenteDto>{

	@Override
	public ProfiloUtenteDto toDto(Utente model) throws Exception {
		ProfiloUtenteDto dto = null;
		if(model!=null) {
			dto = new ProfiloUtenteDto();
			BeanUtils.copyProperties(model, dto, "ruoli");
			dto.setRuoli(new ArrayList<RuoloUtenteDto>());
			dto.setPermessi(new ArrayList<String>());
			if(model.getRuoli()!=null && !model.getRuoli().isEmpty() ) {
				for(Ruolo ruolo : model.getRuoli()) {
					RuoloUtenteDto ruoloUtenteDto = new RuoloUtenteDto(); 
					BeanUtils.copyProperties(ruolo, ruoloUtenteDto, "permessi");
					ruoloUtenteDto.setListaPermessi(new ArrayList<PermessoUtenteDto>());
					if(ruolo.getPermessi()!=null) {
						for(Permesso permesso: ruolo.getPermessi()) {
							if( null!=permesso && !dto.getPermessi().contains(permesso.getCodice())) {
								PermessoUtenteDto permessoUtenteDto = new PermessoUtenteDto(); 
								BeanUtils.copyProperties(permesso, permessoUtenteDto);
								ruoloUtenteDto.getListaPermessi().add(permessoUtenteDto);
								dto.getPermessi().add(permesso.getCodice());
							}
						}
					}
					dto.getRuoli().add(ruoloUtenteDto);
				}
			}
		}
		
		return dto;
	}

	@Override
	public Utente toModel(ProfiloUtenteDto dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ProfiloUtenteDto> toDto(List<Utente> models) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
