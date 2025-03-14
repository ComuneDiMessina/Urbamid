package it.eng.tz.urbamid.administration.mappa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.UMappaLayerRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaPermesso;
import it.eng.tz.urbamid.administration.mappa.dao.model.PermessoLayer;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappa;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappaLayer;
import it.eng.tz.urbamid.administration.util.StringUtil;
import it.eng.tz.urbamid.administration.web.dto.GroupMapDTO;
import it.eng.tz.urbamid.administration.web.dto.LayerDaAggiungereDTO;

/**
 * MS 
 */

@Repository
public class UMappaLayerDao extends  GenericDao<UMappaLayer>{

	private final UMappaLayerRowMapper rowMapper = new UMappaLayerRowMapper();

	public List<UMappaLayer> select(){
		String sql = it.eng.tz.urbamid.administration.util.StringUtil.concateneString("select"
				," id"
				,",id_mappa"
				,",nome_layer"
				,",title_layer"
				,",id_parent"
				,",tipo"
				,",abilitato"
				,",trasparenza"
				,",campo_1"
				,",campo_2"
				,",campo_3"
				,",pos"
				," from u_admin_mappa_layer"
				);
		return getJdbcTemplate().query(sql, this.rowMapper);
	}


	public List<UMappaLayer> getGroup(UMappa entity){
		String sql = it.eng.tz.urbamid.administration.util.StringUtil.concateneString("select"
				," distinct *"
				," from u_admin_mappa_layer"
				," where id_mappa = ?"
				," and tipo = 'G'"
				," order by pos asc"
				);
		List<Object> parameters = new ArrayList<Object>(); // ," order by pos,id_parent asc"
		parameters.add(entity.getId());

		return getJdbcTemplate().query(sql,parameters.toArray(), this.rowMapper);
	}

	public LinkedHashMap<String,List<UMappaLayer>> getGroupLayerByMappa(UMappa entity){
		LinkedHashMap<String,List<UMappaLayer>> ret= new LinkedHashMap<String, List<UMappaLayer>>();

		List<UMappaLayer> group =getGroup(entity);


		String sql = it.eng.tz.urbamid.administration.util.StringUtil.concateneString("select"
				," *"
				," from u_admin_mappa_layer"
				," where id_mappa = ?"
				," and id_parent = ?"
				," and tipo = 'L'"
				," order by pos asc"
				);
		if(group!=null && group.size()>0) {
			for(UMappaLayer ly:group) {
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(entity.getId());
				parameters.add(ly.getIdParent());
				List<UMappaLayer> layers= getJdbcTemplate().query(sql,parameters.toArray(), this.rowMapper);
				ret.put(ly.getIdParent(), layers);
			}
		}


		return ret;


	}


	public long count(){
		String sql = StringUtil.concateneString("select count(*)"
				," from u_admin_mappa_layer"
				);
		return getJdbcTemplate().queryForObject(sql, Long.class);
	}


	public UMappaLayer find(UMappaLayer pk){
		String sql = StringUtil.concateneString("select"
				," id"
				,",id_mappa"
				,",nome_layer"
				,",title_layer"
				,",id_parent"
				,",tipo"
				,",abilitato"
				,",trasparenza"
				,",campo_1"
				,",campo_2"
				,",campo_3"
				,",pos"
				," from u_admin_mappa_layer"
				," where id = ?"
				," and id_mappa = ?"
				);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		parameters.add(pk.getIdMappa());
		return getJdbcTemplate().queryForObject(sql, this.rowMapper, parameters.toArray());
	}



	public long countExist(UMappaLayer entity){
		String sql = StringUtil.concateneString("select count(*)"
				," from u_admin_mappa_layer"
				," WHERE id_mappa = ?"
				," AND nome_layer = ?"
				," AND id_parent = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getIdMappa());
		parameters.add(entity.getNomeLayer());
		parameters.add(entity.getIdParent());

		return getJdbcTemplate().queryForObject(sql,parameters.toArray(), Long.class);
	}  
	public int insert(UMappaLayer entity){
		if(entity==null)
			return 0;
		else if(entity.getIsNew()) {

			//controllo se ci sono layer con lo stessi nome associati	        	
			//if(countExist(entity)<1) {       	 
			List<UMappaLayer>lts=null;   
			if(entity.getTipo().equals("G"))
			{
				//controllo se ci sono gruppi con lo stessi idParend
				//lts=findElementsGruppo(entity);
				//if(lts!=null && lts.size()>0)
				//	return 0;
			}

			String sql = StringUtil.concateneString("insert into u_admin_mappa_layer"
					,"(id"
					,",id_mappa"
					,",nome_layer"
					,",title_layer"
					,",id_parent"
					,",tipo"
					,",abilitato"
					,",trasparenza"
					,",campo_1"
					,",campo_2"
					,",campo_3"
					,",pos"
					,") values "
					,"(nextval('u_admin_mappa_layer_id_seq')"
					,",?"
					,",?"
					,",?"
					,",?"
					,",?"
					,",?"
					,",?"
					,",?"
					,",?"
					,",?"
					,",?"
					,")"
					);
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(entity.getIdMappa());
			parameters.add(entity.getNomeLayer());
			parameters.add(entity.getTitleLayer());
			parameters.add(entity.getIdParent());
			parameters.add(entity.getTipo());
			parameters.add(entity.getAbilitato());
			parameters.add(entity.getTrasparenza());
			parameters.add(entity.getCampo1());
			parameters.add(entity.getCampo2());
			parameters.add(entity.getCampo3());
			parameters.add(entity.getPos());

			return getJdbcTemplate().update(sql, parameters.toArray());
			// }
			/*else {
    		 return 0; 
    	 }*/


		}else {
			return update(entity);        	

		}

	}


	public int update(UMappaLayer entity){
		if(entity==null)
			return 0;
		if(entity.getTrasparenza()!=null && !entity.getTrasparenza().isEmpty())
		{ 
			return updateTrasparenzs(entity); 
		}
		else {

			String sql = StringUtil.concateneString("update u_admin_mappa_layer"
					," set abilitato = ?"
					," where id_mappa = ?"
					," and nome_layer = ?"
					," and id_parent = ?"
					);
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(entity.getAbilitato());
			parameters.add(entity.getIdMappa());
			parameters.add(entity.getNomeLayer());
			parameters.add(entity.getIdParent());

			return getJdbcTemplate().update(sql, parameters.toArray());
		}
	}


	public int updateTrasparenzs(UMappaLayer entity){
		if(entity==null)
			return 0;

		String sql = StringUtil.concateneString("update u_admin_mappa_layer"
				," set trasparenza = ?"
				," where id_mappa = ?"
				," and nome_layer = ?"
				," and id_parent = ?"
				);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getTrasparenza());
		parameters.add(entity.getIdMappa());
		parameters.add(entity.getNomeLayer());
		parameters.add(entity.getIdParent());

		return getJdbcTemplate().update(sql, parameters.toArray());
	}

	//modifica il nome GRUPPO
	public int updateGruppo(UMappaLayer entity){
		if(entity==null)
			return 0;

		String sql = StringUtil.concateneString("update u_admin_mappa_layer"
				," set id_parent = ?"
				," where id_mappa = ?"
				," and id_parent = ?"
				);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCampo1()); //nuovo idparent da modificare
		parameters.add(entity.getIdMappa());
		parameters.add(entity.getIdParent()); // id parent attuale

		return getJdbcTemplate().update(sql, parameters.toArray());
	}

	public  List<UMappaLayer> findElementsGruppo(UMappaLayer pk){
		String sql = StringUtil.concateneString("select"
				," *"
				," from u_admin_mappa_layer"
				," where id_mappa = ?"
				," and id_parent = ?"
				);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getIdMappa());
		parameters.add(pk.getIdParent());
		return getJdbcTemplate().query(sql, parameters.toArray(),this.rowMapper);
	}

	//Elimina il GRUPPO
	public int[] deleteGruppo(UMappaLayer pk) {

		//Elimino l'associazione dei Layer e Gruppi
		List<UMappaLayer> listLayers=findElementsGruppo(pk);
		List<Object[]> batchArgsLayer = new ArrayList<>();
		String sqlLayer = StringUtil.concateneString("delete from public.u_admin_mappa_layer"
				," where id = ? and id_mappa = ?");
		if(listLayers!=null && listLayers.size()>0)
		{
			for(UMappaLayer ly:listLayers)
				batchArgsLayer.add(new Object[] {ly.getId(),ly.getIdMappa()});	

			int[] argTypesLayer = {Types.INTEGER,Types.INTEGER };	
			int[] rows=getJdbcTemplate().batchUpdate(sqlLayer, batchArgsLayer, argTypesLayer);
			return rows;
		}	
		return new int[0];
	}


	public UMappaLayer findDelete(UMappaLayer pk){
		String sql = StringUtil.concateneString("select"
				," *"
				," from u_admin_mappa_layer"
				," where id_mappa = ?"
				," and nome_layer = ?"
				," and id_parent = ?"
				);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getIdMappa());
		parameters.add(pk.getNomeLayer());
		parameters.add(pk.getIdParent());
		return getJdbcTemplate().queryForObject(sql, this.rowMapper, parameters.toArray());
	}

	public int delete(UMappaLayer entity){
		UMappaLayer pk=this.findDelete(entity);
		if(pk!=null) {
			String sql = StringUtil.concateneString("delete from u_admin_mappa_layer"
					," where id = ?"
					," and id_mappa = ?"
					);
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(pk.getId());
			parameters.add(entity.getIdMappa());
			return getJdbcTemplate().update(sql, parameters.toArray());
		}
		return 0;
	}

	public int[] deletes(List<Integer> ids) throws Exception{ 

		String sql = StringUtil.concateneString("delete from u_admin_mappa_layer"
				," where id = ?"
				," and id_mappa = ?"
				);

		int[] argTypes = {Types.INTEGER };	

		List<Object[]> batchArgs = new ArrayList<>();
		for(Integer i : ids)
			batchArgs.add(new Object[] {i.intValue()});

		int[] rows=	getJdbcTemplate().batchUpdate(sql, batchArgs, argTypes);

		return rows;

	}

	public int saveGrups(LinkedHashMap<String, List<UMappaLayer>> grups) throws Exception {
		int idMappa = 0;
		int recEliminati=0;
		// @MS RECUPERO L'ID MAPPA
		if (grups != null && grups.size() > 0) {
			for (String key : grups.keySet()) {
				List<UMappaLayer> layers = grups.get(key);
				if(layers!=null && layers.size()>0 && layers.get(0).getIdMappa()>0)
				{	 
					idMappa=layers.get(0).getIdMappa();
					break;
				}

			}
		}

		String recuperoPermessi = "SELECT layer.id, layer.nome_layer, permesso.id_ruolo, permesso.abilita_visualizzazione, permesso.abilita_modifica\r\n" + 
								  "FROM public.u_admin_risorsa_permesso permesso, public.u_admin_mappa_layer layer\r\n" + 
								  "WHERE permesso.id_risorsa = layer.id AND permesso.id_tipo_risorsa = 2 AND layer.id_mappa = " + idMappa;

		List<PermessoLayer> permessiSalvati = getJdbcTemplate().query(recuperoPermessi, new RowMapper<PermessoLayer>() {

			@Override
			public PermessoLayer mapRow(ResultSet rs, int rowNum) throws SQLException {

				PermessoLayer permesso = new PermessoLayer();

				permesso.setId_layer(rs.getLong("id"));
				permesso.setNome_layer(rs.getString("nome_layer"));
				permesso.setId_ruolo(rs.getLong("id_ruolo"));
				permesso.setAbilita_visualizzazione(rs.getBoolean("abilita_visualizzazione"));
				permesso.setAbilita_modifica(rs.getBoolean("abilita_modifica"));

				return permesso;
			}
		});

		// @MS ELIMINO TUTTI I RECORD CON IDMAPPA UGUALE
		if(idMappa>0) {
			String query="delete from u_admin_mappa_layer where id_mappa ="+idMappa;
			recEliminati=this.getJdbcTemplate().update(query);
		}

		// @MS RICREO I GRUPPI
		if (recEliminati>=0 && idMappa>0 && grups != null && grups.size() > 0) {
			int count=1;
			for (String key : grups.keySet()) {
				UMappaLayer u = new UMappaLayer();
				u.setIdParent(key);
				u.setIdMappa(idMappa);
				u.setTipo("G");
				u.setIsNew(true);
				u.setPos(count);
				if(key.equalsIgnoreCase("DATI DI BASE"))
					u.setPos(count);	
				count++;
				this.insert(u);

			}
		}
		// @MS RICREO I LAYER ASSOCIATI
		if (recEliminati>=0 && idMappa>0 && grups != null && grups.size() > 0) {
			for (String key : grups.keySet()) {
				if(grups.containsKey(key)) {
					List<UMappaLayer> layers = grups.get(key);
					if(layers!=null && layers.size()>0)
						for (UMappaLayer lyr : layers) {
							if(lyr!=null) {
								lyr.setIsNew(true);
								lyr.setIdParent(key);
								lyr.setTipo("L");
								this.insert(lyr);
								this.insertPermessi(lyr, permessiSalvati);
							}
						}
				}
			}
		}

		return recEliminati;
	}

	
	public void insertPermessi(UMappaLayer lyr, List<PermessoLayer> permessiSalvati) {
		
		boolean isInserito = false;
		
		if(permessiSalvati.size() != 0 && !isInserito)  {

			List<Object[]> insertPermessiSalvati = new ArrayList<>();

			for (int i = 0; i < permessiSalvati.size(); i++) {

				//ELIMINO DALLA TABELLA U_ADMIN_RISORSA_PERMESSO TUTTI I PERMESSI ASSOCIATI AL VECCHIO ID DEI LAYER 
				String sqlDelete = StringUtil.concateneString("DELETE FROM public.u_admin_risorsa_permesso WHERE id_risorsa = ? AND id_ruolo = ? AND id_tipo_risorsa = 2");
				getJdbcTemplate().update(sqlDelete, new Object[] { permessiSalvati.get(i).getId_layer(),
																   permessiSalvati.get(i).getId_ruolo() });
				
				//CONTROLLO SE IL NOME DEL LAYER CORRISPONDA A QUELLO SALVATO NELLA LISTA PERMESSISALVATI
				if(lyr.getNomeLayer().equals(permessiSalvati.get(i).getNome_layer())) {
					
					//RECUPERO IL NUOVO ID DEL LAYER DAL DB
					String sqlIdLayer = StringUtil.concateneString("SELECT id FROM public.u_admin_mappa_layer WHERE id_mappa = ? AND nome_layer = ? AND tipo = 'L'");

					List<Long> idLayer = getJdbcTemplate().query(sqlIdLayer, new RowMapper<Long>() {

						@Override
						public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
							return rs.getLong("id");
						}

					}, new Object[] { lyr.getIdMappa(),
									  lyr.getNomeLayer() });

					insertPermessiSalvati.add(new Object[] { idLayer.get(0),
															 permessiSalvati.get(i).getId_ruolo(),
															 permessiSalvati.get(i).isAbilita_visualizzazione(),
															 permessiSalvati.get(i).isAbilita_modifica() });

				}

			}

			if(insertPermessiSalvati.size() != 0) {
				isInserito = true;
				//ESEGUO L'INSERIMENTO 
				String sqlPermessiSalvati = StringUtil.concateneString("INSERT INTO public.u_admin_risorsa_permesso(id_rsrs_prms, id_risorsa, id_tipo_risorsa, id_ruolo, abilita_visualizzazione, abilita_modifica) VALUES (nextval('u_admin_mappa_mappermesso_id_seq'), ?, 2, ?, ?, ?)");
				getJdbcTemplate().batchUpdate(sqlPermessiSalvati, insertPermessiSalvati);
			}

		}

		//CONTROLLO SE IN ALTRE MAPPE CI SIANO DEI PERMESSI ASSOCIATI AL NOME DEL LAYER
		String sql = StringUtil.concateneString("SELECT DISTINCT id_ruolo, abilita_visualizzazione, abilita_modifica FROM public.u_admin_risorsa_permesso WHERE id_risorsa IN (SELECT id FROM public.u_admin_mappa_layer WHERE nome_layer = ?)");

		List<MappaPermesso> permessi = getJdbcTemplate().query(sql, new RowMapper<MappaPermesso>() {

			@Override
			public MappaPermesso mapRow(ResultSet rs, int rowNum) throws SQLException {

				MappaPermesso permesso = new MappaPermesso();

				permesso.setId_ruolo(rs.getLong("id_ruolo"));
				permesso.setAbilita_visualizzazione(rs.getBoolean("abilita_visualizzazione"));
				permesso.setAbilita_modifica(rs.getBoolean("abilita_modifica"));

				return permesso;
			}

		}, lyr.getNomeLayer());

		if(permessi.size() != 0) {

			List<Object[]> batchInsert = new ArrayList<>();
			List<Object[]> batchDelete = new ArrayList<>();
			
			for (int i = 0; i < permessi.size(); i++) {

				String sqlIdLayer = StringUtil.concateneString("SELECT id FROM public.u_admin_mappa_layer WHERE id_mappa = ? AND nome_layer = ? AND tipo = 'L'");

				List<Long> idLayer = getJdbcTemplate().query(sqlIdLayer, new RowMapper<Long>() {

					@Override
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getLong("id");
					}

				}, new Object[] { lyr.getIdMappa(),
								  lyr.getNomeLayer() });

				batchInsert.add(new Object[] { idLayer.get(0),
											   permessi.get(i).getId_ruolo(),
											   permessi.get(i).getAbilita_visualizzazione(),
											   permessi.get(i).getAbilita_modifica() });
					
				batchDelete.add(new Object[] { idLayer.get(0),
											   permessi.get(i).getId_ruolo() });

			}

			if(batchDelete.size() != 0) {
				
				String sqlDelete = StringUtil.concateneString("DELETE FROM public.u_admin_risorsa_permesso WHERE id_risorsa = ? AND id_ruolo = ? AND id_tipo_risorsa = 2");
				getJdbcTemplate().batchUpdate(sqlDelete, batchDelete);
				
			}
			
			if(batchInsert.size() != 0) {

				String sqlInsert = StringUtil.concateneString("INSERT INTO public.u_admin_risorsa_permesso(id_rsrs_prms, id_risorsa, id_tipo_risorsa, id_ruolo, abilita_visualizzazione, abilita_modifica) VALUES (nextval('u_admin_mappa_mappermesso_id_seq'), ?, 2, ?, ?, ?)");
				getJdbcTemplate().batchUpdate(sqlInsert, batchInsert);

			}

		}	

	}
	
	/**
	 * Recupera le tavole di tutte le mappe
	 * @param idMappa
	 * @return
	 * @throws Exception
	 */
	public List<GroupMapDTO> getGroupTableMap(final Integer idMappa) throws Exception {
		logger.info("START >>> getGroupTableMap");
		try {
			final StringBuilder sb = new StringBuilder("SELECT uam.id, uam.title, array_agg(DISTINCT uaml.id_parent) AS tavole")
											.append(" FROM u_admin_mappa_layer uaml JOIN u_admin_mappa uam ON uaml.id_mappa = uam.id")
											.append(" WHERE uam.stato = 'P' AND id_parent <> 'DATI DI BASE' AND uam.id <> ?")
											.append(" GROUP BY uam.title, uam.id")
											.append(" ORDER BY uam.title ASC");
			
			final List<GroupMapDTO> listGroup = getJdbcTemplate().query(sb.toString(), new RowMapper<GroupMapDTO>() {

				@Override
				public GroupMapDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					GroupMapDTO result = new GroupMapDTO();
					
					result.setIdMappa(rs.getLong("id"));
					result.setTavoleGroup(rs.getString("title"));
					result.setTavole(Arrays.asList((String[]) rs.getArray("tavole").getArray()));
					
					return result;
				}
				
			}, idMappa);
			
			return listGroup;
		} finally {
			logger.info("END <<< getGroupTableMap");
		
		}
	}
	
	/**
	 * Recupera i layer di una tavola per una determinata mappa
	 * @param idMappa
	 * @param nomeTavola
	 * @return
	 * @throws Exception
	 */
	public List<LayerDaAggiungereDTO> layerDaAggiungere(final Integer idMappa, final String nomeTavola) throws Exception {
		logger.info("START >>> layerDaAggiungere");
		try {
			final StringBuilder sb = new StringBuilder("SELECT nome_layer, title_layer, trasparenza, pos")
											.append(" FROM u_admin_mappa_layer")
											.append(" WHERE id_mappa = ? and tipo = 'L' and id_parent = ?");
			
			final List<LayerDaAggiungereDTO> listGroup = getJdbcTemplate().query(sb.toString(), new RowMapper<LayerDaAggiungereDTO>() {

				@Override
				public LayerDaAggiungereDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					LayerDaAggiungereDTO result = new LayerDaAggiungereDTO();
					
					result.setNomeLayer(rs.getString("nome_layer"));
					result.setTitoloLayer(rs.getString("title_layer"));
					result.setTrasparenza(rs.getString("trasparenza"));
					result.setPosizione(rs.getInt("pos"));
					
					return result;
				}
				
			}, new Object[] { idMappa,
							  nomeTavola });
			
			return listGroup;
		} finally {
			logger.info("END <<< getGroupTableMap");
		
		}
	}
	
}