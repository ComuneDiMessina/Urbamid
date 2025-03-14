package it.eng.tz.urbamid.administration.mappa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import it.eng.tz.urbamid.administration.dao.GenericDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaRicercaRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.MappaToolRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.RicercaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaMappeBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.UMappaLayerRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.UMappaRowMapper;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaRicerca;
import it.eng.tz.urbamid.administration.mappa.dao.model.MappaTool;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappa;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappaLayer;
import it.eng.tz.urbamid.administration.util.StringUtil;

@Repository
public class UMappaDao extends GenericDao<UMappa>{

	private final UMappaRowMapper rowMapper = new UMappaRowMapper();


	public List<UMappa> select(){
		/*String sql = StringUtil.concateneString("select"
                                               ," id"
                                               ,",codice"
                                               ,",nome"
                                               ,",descrizione"
                                               ,",stato"
                                               ,",mappa_predefinita"
                                               ,",data_creazione"
                                               ,",data_modifica"
                                               ,",utente_creazione"
                                               ,",utente_modifica"
                                               ," from public.u_admin_umappa"
                                               ); */

		String sql = StringUtil.concateneString("select * from public.u_admin_mappa" );
		return getJdbcTemplate().query(sql, this.rowMapper);
	}


	public long count(){
		/*String sql = StringUtil.concateneString("select count(*)"
                                               ," from public.u_admin_umappa"
                   );*/

		String sql = StringUtil.concateneString("select count(*) from public.u_admin_mappa");
		return getJdbcTemplate().queryForObject(sql, Long.class);
	}


	public UMappa find(UMappa pk){
		/*String sql = StringUtil.concateneString("select"
                                               ," id"
                                               ,",codice"
                                               ,",nome"
                                               ,",descrizione"
                                               ,",stato"
                                               ,",mappa_predefinita"
                                               ,",data_creazione"
                                               ,",data_modifica"
                                               ,",utente_creazione"
                                               ,",utente_modifica"
                                               ," from u_admin_umappa"
                                               ," where id = ?"
                                               ); */
		String sql = "select * from u_admin_mappa where id = ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getId());
		return getJdbcTemplate().queryForObject(sql, this.rowMapper, parameters.toArray());
	}

	public List<UMappa> findCode(UMappa pk){
	 
		String sql = "select * from u_admin_mappa where code = ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(pk.getCodice());
		return getJdbcTemplate().query(sql, parameters.toArray(),this.rowMapper);
	}
	

	public int insert(UMappa entity){
		if(entity==null)
			return 0;
		else if(entity.getIsNew()) {
			
			//controllo se il e gia codice esistente
			List<UMappa> lts=findCode(entity);
			if(lts!=null && lts.size()>0)
			{
				return 0;
				
			}
			
			entity.setDataCreazione(new Date());
			Integer id=this.getNewId("u_admin_mappa_id_seq");

			entity.setId(id.longValue());

			/*String sql = StringUtil.concateneString("insert into public.u_admin_umappa"
                                               ,"(id"
                                               ,",codice"
                                               ,",nome"
                                               ,",descrizione"
                                               ,",stato"
                                               ,",mappa_predefinita"
                                               ,",data_creazione"
                                               ,",data_modifica"
                                               ,",utente_creazione"
                                               ,",utente_modifica"
                                               ,") values "
                                               ,"(?"
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
                                               ); */


			String sql = StringUtil.concateneString("insert into public.u_admin_mappa"
					,"(id"
					,",code"
					,",title"
					,",description"
					,",stato"
					,",mappa_predefinita"
					,",data_creazione"
					,",data_modifica"
					,",utente_creazione"
					,",utente_modifica"
					,",show_catalog"
					,",zoom"
					,") values "
					,"(?"
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
			parameters.add(entity.getId());
			parameters.add(entity.getCodice());
			parameters.add(entity.getNome());
			parameters.add(entity.getDescrizione());
			parameters.add(entity.getStato());
			parameters.add(entity.getMappaPredefinita());
			parameters.add(entity.getDataCreazione());
			parameters.add(entity.getDataModifica());
			parameters.add(entity.getUtenteCreazione());
			parameters.add(entity.getUtenteModifica());
			parameters.add(entity.getShowCatalog());
			parameters.add(entity.getZoom());
			int[] argTypes = {Types.INTEGER,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.BOOLEAN,Types.TIMESTAMP,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.INTEGER  };
			int action= getJdbcTemplate().update(sql, parameters.toArray(),argTypes);
			if(action>0)
				return id.intValue();
			else
				return action;

		}else {
			return this.update(entity);
		}
	}


	public int update(UMappa entity){
		if(entity==null)
			return 0;

		entity.setDataModifica(new Date());

		/*String sql = StringUtil.concateneString("update public.u_admin_umappa"
                                               ," set codice = ?"
                                               ,", nome = ?"
                                               ,", descrizione = ?"
                                               ,", stato = ?"
                                               ,", mappa_predefinita = ?"
                                               ,", data_modifica = ?"
                                               ,", utente_modifica = ?"
                                               ," where id = ?"
                                               );*/

		String sql = StringUtil.concateneString("update public.u_admin_mappa"
				," set code = ?"
				,", title = ?"
				,", description = ?"
				,", stato = ?"
				,", mappa_predefinita = ?"
				,", data_modifica = ?"
				,", utente_modifica = ?"
				,", show_catalog = ?"
				,", zoom = ?"
				," where id = ?"
				);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getCodice());
		parameters.add(entity.getNome());
		parameters.add(entity.getDescrizione());
		parameters.add(entity.getStato());
		parameters.add(entity.getMappaPredefinita());
		parameters.add(entity.getDataModifica());
		parameters.add(entity.getUtenteModifica());
		parameters.add(entity.getShowCatalog());
		parameters.add(entity.getZoom());
		parameters.add(entity.getId());
		int[] argTypes = {Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.BOOLEAN,Types.TIMESTAMP,Types.VARCHAR,Types.BOOLEAN,Types.INTEGER,Types.INTEGER  };
		return getJdbcTemplate().update(sql, parameters.toArray(),argTypes);
	}

	public int updateZoomAndShowCat(UMappa entity){
		if(entity==null)
			return 0;

		entity.setDataModifica(new Date());

		String sql = StringUtil.concateneString("update public.u_admin_mappa"
				," set data_modifica = ?"
				,", utente_modifica = ?"
				,", show_catalog = ?"
				,", zoom = ?"
				," where id = ?"
				);
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getDataModifica());
		parameters.add(entity.getUtenteModifica());
		parameters.add(entity.getShowCatalog());
		parameters.add(entity.getZoom());
		parameters.add(entity.getId());
		int[] argTypes = {Types.TIMESTAMP,Types.VARCHAR,Types.BOOLEAN,Types.INTEGER,Types.INTEGER  };
		return getJdbcTemplate().update(sql, parameters.toArray(),argTypes);
	}
	
	public int delete(UMappa entity){
		/*String sql = StringUtil.concateneString("delete from public.u_admin_umappa"
                                               ," where id = ?"
                                               ); */
		String sql = StringUtil.concateneString("delete from public.u_admin_mappa" ," where id = ?");
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(entity.getId());
		return getJdbcTemplate().update(sql, parameters.toArray());
	}

	public int[] deletes(List<Long> ids) throws Exception{ 

		if(ids==null || ids.size()<1)
			return new int[0];

		//Elimino l'associazione dei Layer e Gruppi
		List<UMappaLayer> listLayers=getIDMappaLayer(ids);
		List<Object[]> batchArgsLayer = new ArrayList<>();
		String sqlLayer = StringUtil.concateneString("delete from public.u_admin_mappa_layer"
				," where id = ? and id_mappa = ?");
		if(listLayers!=null && listLayers.size()>0)
		{
			for(UMappaLayer ly:listLayers)
				batchArgsLayer.add(new Object[] {ly.getId(),ly.getIdMappa()});	

			int[] argTypesLayer = {Types.INTEGER,Types.INTEGER };	
			int[] rows_ly=getJdbcTemplate().batchUpdate(sqlLayer, batchArgsLayer, argTypesLayer);

		}

		int[] argTypes = {Types.INTEGER };	 
		String sqlTema = StringUtil.concateneString("delete from public.u_admin_mappa_tema"
				," where id = ?");

		//Elimino l'associazione dei Temi
		List<Long> listaId=getIdsMappaTemas(ids);
		List<Object[]> batchArgsLong = new ArrayList<>();
		if(listaId!=null && listaId.size()>0)
		{
			for(Long lg:listaId)
				batchArgsLong.add(new Object[] {lg.intValue()});	

			int[] rows_=getJdbcTemplate().batchUpdate(sqlTema, batchArgsLong, argTypes);

		}

		// elimino l'associazione con le ricerche e con i tools
		String sqlDeleteRicerche = StringUtil.concateneString("delete from public.u_admin_mappa_mapricerca"
				," where id_mappa = ?");
		String sqlDeleteTools = StringUtil.concateneString("delete from public.u_admin_mappa_maptool"
				," where id_mappa = ?");

		for(Long idMappa : ids) 
		{
			getJdbcTemplate().update(sqlDeleteRicerche, idMappa);
			getJdbcTemplate().update(sqlDeleteTools, idMappa);
		}




		/*String sql = StringUtil.concateneString("delete from public.u_admin_umappa"
                  ," where id = ?");*/

		String sql = StringUtil.concateneString("delete from public.u_admin_mappa"
				," where id = ?");


		// Elimino le Mappe
		List<Object[]> batchArgs = new ArrayList<>();
		for(Long i : ids)
			batchArgs.add(new Object[] {i.longValue()});

		int[] rows=	getJdbcTemplate().batchUpdate(sql, batchArgs, argTypes);

		return rows;

	}
	public List<Long> getIdsMappaTemas(List<Long> ids) throws Exception{ 
		String sql = "SELECT u.id FROM public.u_admin_mappa_tema u,public.u_admin_tema t WHERE u.id_tema=t.id AND u.id_mappa IN(:ids)";
		NamedParameterJdbcTemplate template=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		List<Long> listaId = template.query(sql, parameters, new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {

				return new Long(rs.getLong("id"));
			}
		});



		return listaId;

	}	

	public List<UMappaLayer> getIDMappaLayer(List<Long> ids) throws Exception{ 
		String sql = "SELECT u.id ,u.id_mappa FROM public.u_admin_mappa_layer u WHERE u.id_mappa IN(:ids)";
		NamedParameterJdbcTemplate template=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		List<UMappaLayer> listaId = template.query(sql, parameters, new RowMapper<UMappaLayer>() {
			@Override
			public UMappaLayer mapRow(ResultSet rs, int rowNum) throws SQLException {
				UMappaLayer bean= new UMappaLayer();
				bean.setId(rs.getInt("id"));
				bean.setIdMappa(rs.getInt("id_mappa"));
				return bean;
			}
		});



		return listaId;

	}


	public List<TemaBean> getTemaToMappa(List<Long> ids) throws Exception{ 
		String sql = "SELECT u.id_tema, t.nome FROM public.u_admin_mappa_tema u,public.u_admin_tema t WHERE u.id_tema=t.id AND u.id_mappa IN(:ids)";
		NamedParameterJdbcTemplate template=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		List<TemaBean> listaId = template.query(sql, parameters, new RowMapper<TemaBean>() {
			@Override
			public TemaBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				TemaBean bean= new TemaBean();
				bean.setId(rs.getLong("id_tema"));
				bean.setNome(rs.getString("nome"));
				return bean;
			}
		});



		return listaId;

	}

	public int[] associaTemaToMappa(UMappa entity) throws Exception{ 
		String sql = "INSERT INTO public.u_admin_mappa_tema (id, id_tema, id_mappa) VALUES(nextval('public.u_mappa_tema_id_seq'),?,?);";
		if(entity!=null && entity.getIdsTemi()!=null && entity.getId()!=null) {

			int[] argTypes = {Types.INTEGER,Types.INTEGER};	

			List<Object[]> batchArgs = new ArrayList<>();
			for(Object obj : entity.getIdsTemi())
				batchArgs.add(new Object[] {obj,entity.getId()});

			int[] rows=	getJdbcTemplate().batchUpdate(sql, batchArgs, argTypes);

			return rows;

		}
		else
			return new int[0];

	}

	public int[] deleteTemaToMappa(UMappa entity) throws Exception{ 
		String sql ="DELETE FROM public.u_admin_mappa_tema WHERE id_tema=? and id_mappa=?;";

		if(entity!=null && entity.getIdsTemi()!=null && entity.getId()!=null) {

			int[] argTypes = {Types.INTEGER,Types.INTEGER};	

			List<Object[]> batchArgs = new ArrayList<>();
			for(Object obj : entity.getIdsTemi())
				batchArgs.add(new Object[] {obj,entity.getId()});

			int[] rows=getJdbcTemplate().batchUpdate(sql, batchArgs, argTypes);

			return rows;

		}
		else
			return new int[0];

	}

	public List<TemaBean> getTemaToMappa(UMappa entity){
		String sql = "SELECT u.id_tema, t.nome FROM public.u_admin_mappa_tema u,public.u_admin_tema t WHERE u.id_tema=t.id AND u.id_mappa=?";

		List<TemaBean> listaId = getJdbcTemplate().query(sql, new Object[] { entity.getId()}, new RowMapper<TemaBean>() {
			@Override
			public TemaBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				TemaBean bean= new TemaBean();
				bean.setId(rs.getLong("id_tema"));
				bean.setNome(rs.getString("nome"));

				return bean;
			}
		});

		return listaId;
	}

	public List<TemaMappeBean> getAllTemaMappe() throws Exception{ 
		String sql = "SELECT ut.id idTema, ut.ordinamento ordinamento, um.stato stato, ut.nome nomeTema, um.id idMappa, um.title nomeMappa, um.code codeMappa, ut.ordinamento " +
				"FROM public.u_admin_mappa um, public.u_admin_mappa_tema umt,public.u_admin_tema ut " +
				"WHERE " +
				"um.id=umt.id_mappa AND " +
				"umt.id_tema=ut.id " + 
				" ORDER BY ut.nome ASC, ut.ordinamento ASC";
		NamedParameterJdbcTemplate template=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		List<TemaMappeBean> listaId = template.query(sql, parameters, new RowMapper<TemaMappeBean>() {
			@Override
			public TemaMappeBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				TemaMappeBean bean= new TemaMappeBean();
				bean.setIdTema(rs.getLong("idTema"));
				bean.setNomeTema(rs.getString("nomeTema"));
				bean.setIdMappa(rs.getLong("idMappa"));
				bean.setNomeMappa(rs.getString("nomeMappa"));
				bean.setCodeMappa(rs.getString("codeMappa"));
				bean.setOrdinamento(rs.getLong("ordinamento"));
				bean.setStato(rs.getString("stato"));
				return bean;
			}
		});



		return listaId;

	}
	
	
	public List<TemaMappeBean> getAllTemaMappebyRoles(List<Long> roles) throws Exception{ 
		/*String sql = "SELECT ut.id idTema, ut.nome nomeTema, um.id idMappa, um.title nomeMappa, um.code codeMappa " +
				"FROM public.u_admin_mappa um, public.u_admin_mappa_tema umt,public.u_admin_tema ut " +
				"WHERE " +
				"um.id=umt.id_mappa AND " +
				"umt.id_tema=ut.id"; */
		
		
		String sql = StringUtil.concateneString("SELECT ut.id idTema, mappa.stato stato, ut.ordinamento ordinamento, ut.nome nomeTema, mappa.id idMappa, mappa.title nomeMappa, mappa.code codeMappa from"
				," (select mapparuoli.* from ( ",
				"SELECT um.id , um.title , um.code , rp.id_ruolo ruolo, um.stato stato ",
				"FROM public.u_admin_mappa um ",
				"LEFT OUTER JOIN public.u_admin_risorsa_permesso rp ",
				"on rp.id_risorsa = um.id and rp.id_tipo_risorsa = 1) mapparuoli ",
				"where (mapparuoli.ruolo is null or mapparuoli.ruolo IN (:ids))) mappa, ",
				"public.u_admin_mappa_tema umt,public.u_admin_tema ut ",
				"WHERE ",
				"mappa.id=umt.id_mappa AND ",
				"umt.id_tema=ut.id ",
				"ORDER BY ut.ordinamento ASC, ut.nome ASC"
				);	
		
		NamedParameterJdbcTemplate template=new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", roles);
		List<TemaMappeBean> listaId = template.query(sql, parameters, new RowMapper<TemaMappeBean>() {
			
			@Override
			public TemaMappeBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				TemaMappeBean bean= new TemaMappeBean();
				bean.setIdTema(rs.getLong("idTema"));
				bean.setNomeTema(rs.getString("nomeTema"));
				bean.setIdMappa(rs.getLong("idMappa"));
				bean.setNomeMappa(rs.getString("nomeMappa"));
				bean.setCodeMappa(rs.getString("codeMappa"));
				bean.setOrdinamento(rs.getLong("ordinamento"));
				bean.setStato(rs.getString("stato"));
				return bean;
			}
		});



		return listaId;

	}
	
	
	
	public int duplica(UMappa uMappa)throws Exception {
		Integer cod=this.getNewId("u_admin_mappa_duplicate_id_seq");
		
		Long idMappaOriginal= uMappa.getId();
		UMappa duplicato=find(uMappa);
		duplicato.setIsNew(true);
		duplicato.setCodice(duplicato.getCodice()+"-DUPL-"+cod);
		//CREO LA NUOVA MAPPA DUPLICATA
		int idMappaDuplicata=insert(duplicato);
		if(idMappaDuplicata>0) {
		duplicato.setId(new Long(idMappaDuplicata));
		//RECUPERO I LAYER ASSOCIATI E LI ASSOCIO ALLA NUOVA MAPPA DUPLICATA
		List<UMappaLayer> layers=getLayerByIdMappa(idMappaOriginal);
		 List<Object[]> batchInsertLayer = new ArrayList<>();
		if(layers!=null && layers.size()>0)
		for(UMappaLayer entity: layers)
		{
			batchInsertLayer.add(new Object[] { idMappaDuplicata, 
		    		   entity.getNomeLayer(),
		    		   entity.getTitleLayer(),
		    		   entity.getIdParent(),
		    		   entity.getTipo(),
		    		   entity.getAbilitato(),
		    		   entity.getTrasparenza(),
		    		   entity.getCampo1(),
		    		   entity.getCampo2(),
		    		   entity.getCampo3()});	
		
		}
		
		
		 int[] argLayer = {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BOOLEAN,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
			
			String sqlLayer="insert into u_admin_mappa_layer(id,id_mappa,nome_layer,title_layer,id_parent,tipo,abilitato,trasparenza,campo_1,campo_2,campo_3) values (nextval('u_admin_mappa_layer_id_seq'),?,?,?,?,?,?,?,?,?,?)";     			
			if(batchInsertLayer.size() > 0) {
				getJdbcTemplate().batchUpdate(sqlLayer, batchInsertLayer,argLayer);		
			}  
	     

	   //RECUPERO I TEMI ASSOCIATI E LI ASSOCIO ALLA NUOVA MAPPA DUPLICATA
		List<Long> idTemi=getTemaIdByIdMappa(idMappaOriginal);
		if(idTemi!=null && idTemi.size()>0)
		{ duplicato.setIdsTemi(idTemi.toArray());
			associaTemaToMappa(duplicato);
		}
		
		//RECUPERO I MAP_RICERCA ASSOCIATI E LI ASSOCIO ALLA NUOVA MAPPA DUPLICATA
		int[] argRicerca = {Types.INTEGER,Types.INTEGER};	
		List<Long>  mapRicerca=getIdMapRicercaByIdMappa(idMappaOriginal);
		List<Object[]> batchInsertRicerca = new ArrayList<>();
		if(mapRicerca!=null && mapRicerca.size()>0)
		for(Long l : mapRicerca) {
		  
			batchInsertRicerca.add(new Object[] { idMappaDuplicata, l});
			}
			
		String sqlRicerca = "INSERT INTO u_admin_mappa_mapricerca(id_mappa_ricerca, id_mappa, id_mapricerca) VALUES (nextval('u_admin_mappa_mapparicerca_id_seq'), ?, ?)";
		if(batchInsertRicerca.size() > 0) {
			getJdbcTemplate().batchUpdate(sqlRicerca, batchInsertRicerca,argRicerca);		
		}
		
		//RECUPERO I MAP_TOOL ASSOCIATI E LI ASSOCIO ALLA NUOVA MAPPA DUPLICATA
		List<Long> mappaTools=getMappaToolByIdMappa(idMappaOriginal);
		
		int[] argTool = {Types.INTEGER,Types.INTEGER};	
		List<Object[]> batchInsertTool = new ArrayList<>();
		if(mappaTools!=null && mappaTools.size()>0)
		for(Long l : mappaTools) {
		  
			batchInsertTool.add(new Object[] { idMappaDuplicata, l});
			}
			
		String sqlTool = "INSERT INTO u_admin_mappa_maptool(id_mappa_tool, id_mappa, id_maptool) VALUES (nextval('u_admin_mappa_maptool_id_seq'), ?, ?)";
		if(batchInsertTool.size() > 0) {
			getJdbcTemplate().batchUpdate(sqlTool, batchInsertTool,argTool);		
		}
		
		
		
		}
		
		//RITORNO L'ID DELLA NUOVA MAPPA DUPLICATA
		return idMappaDuplicata;
	}

    public List<UMappaLayer> getLayerByIdMappa(Long pk) throws Exception{
    	String sql ="select * from public.u_admin_mappa_layer where id_mappa =?";
    	 
        List<Object> parameters = new ArrayList<Object>();
        parameters.add(pk);
        return getJdbcTemplate().query(sql, parameters.toArray(), new UMappaLayerRowMapper());
    }
    
	public List<Long> getMappaToolByIdMappa(Long idMappa) throws Exception{ 
		String sql ="select id_maptool from public.u_admin_mappa_maptool where id_mappa =?";
		List<Long> listaId = getJdbcTemplate().query(sql,new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {

				return new Long(rs.getInt("id_maptool"));
			}
		},idMappa);

		return listaId;

	}
	public List<Long> getIdMapRicercaByIdMappa(Long idMappa) throws Exception{ 
		String sql = "SELECT id_mapricerca FROM public.u_admin_mappa_mapricerca WHERE id_mappa =?";
		List<Long> listaId = getJdbcTemplate().query(sql,new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {

				return new Long(rs.getInt("id_mapricerca"));
			}
		},idMappa);

		return listaId;

	}
	
	public List<Long> getTemaIdByIdMappa(Long idMappa) throws Exception{ 
		String sql = "SELECT id_tema FROM public.u_admin_mappa_tema WHERE id_mappa =?";
		List<Long> listaId = getJdbcTemplate().query(sql,new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {

				return new Long(rs.getInt("id_tema"));
			}
		},idMappa);

		return listaId;

	}
	
    public int insertLayer(UMappaLayer entity){
   	 if(entity==null)
   	    	return 0;
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
 
       return getJdbcTemplate().update(sql, parameters.toArray());
   
   }
	
}