package it.eng.tz.urbamid.administration.mappa.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.eng.tz.urbamid.administration.mappa.dao.UMappaDao;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaBean;
import it.eng.tz.urbamid.administration.mappa.dao.mapper.TemaMappeBean;
import it.eng.tz.urbamid.administration.mappa.dao.model.UMappa;


@Service
public class UMappaService implements IUMappaService{
	private static final Logger logger = LoggerFactory.getLogger(UMappaService.class.getName());
	
	@Autowired
	UMappaDao umappaDao;
	
	@Override
	public List<UMappa> getMappe() throws Exception {
		String idLog = "getMappe";		
		
		try{
			logger.info("START >>> " + idLog);
			List<UMappa> resultModel =  umappaDao.select();
		 
			return resultModel;
		} catch (Exception e) {
			logger.error(idLog + " >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< " + idLog);
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public String saveOrUpdate(UMappa entity) throws Exception {
		
		try{
			logger.info("START >>> salvaOrUpdate","UMappa ID[{}]",entity!=null?entity.getId():null);
		 
		 
			return String.valueOf(umappaDao.insert(entity));
			
		} catch (Exception e) {
			logger.error( "salvaOrUpdate  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< salvaOrUpdate" );
		}
		
	 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int[] deletes(List<Long> ids) throws Exception {
		 
		try{
			logger.info("START >>> deletes","List ID SIZE[{}]",ids!=null?ids.size():null);
		 
		 
			return umappaDao.deletes(ids);
			
		} catch (Exception e) {
			logger.error( "deletes  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< deletes" );
		}
		
		 
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int delete(UMappa entity) throws Exception {
		try{
			logger.info("START >>> delete","UMappa ID[{}]",entity!=null?entity.getId():null);
		 
		 
			return umappaDao.delete(entity);
			
		} catch (Exception e) {
			logger.error( "delete  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< delete" );
		}
		 
	}

	@Override
	public List<TemaBean> temaToMappa(UMappa entity) throws Exception {
		try{
			logger.info("START >>> temaToMappa","UMappa ID[{}]",entity!=null?entity.getId():null);
		 
		 
			return umappaDao.getTemaToMappa(entity);
			
		} catch (Exception e) {
			logger.error( "temaToMappa  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< temaToMappa" );
		}
		 
	}

	@Override
	public List<TemaBean> temaToMappa(List<Long> ids) throws Exception {
		try{
			logger.info("START >>> temaToMappa","List ID SIZE[{}]",ids!=null?ids.size():null);
		 
		 
			return umappaDao.getTemaToMappa(ids);
			
		} catch (Exception e) {
			logger.error( "temaToMappa  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< temaToMappa" );
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int[] associaTemaToMappa(UMappa entity) throws Exception {
		try{
			logger.info("START >>> associaTemaToMappa","ID[{}]",entity!=null?entity.getId():null);
		 
		 
			return umappaDao.associaTemaToMappa(entity);
			
		} catch (Exception e) {
			logger.error( "associaTemaToMappa  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< associaTemaToMappa" );
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int[] deleteTemaToMappa(UMappa entity) throws Exception {
		try{
			logger.info("START >>> deleteTemaToMappa","ID[{}]",entity!=null?entity.getId():null);
		 
		 
			return umappaDao.deleteTemaToMappa(entity);
			
		} catch (Exception e) {
			logger.error( "deleteTemaToMappa  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< deleteTemaToMappa" );
		}
	}

 
	@Override
	public List<TemaMappeBean> getAllTemaMappe() throws Exception {
		try{
			logger.info("START >>> getAllTemaMappe");
		 
		 
			return umappaDao.getAllTemaMappe();
			
		} catch (Exception e) {
			logger.error( "temaToMappa  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< temaToMappa" );
		}
		 
	}
	
	@Override
	public List<TemaMappeBean> getAllTemaMappeByRoles(List<Long> roles) throws Exception {
		try{
			logger.info("START >>> getAllTemaMappeByRoles");
		 
			return umappaDao.getAllTemaMappebyRoles(roles);
			
		} catch (Exception e) {
			logger.error( "temaToMappa  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< temaToMappa" );
		}
		 
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int duplica(UMappa entity) throws Exception {
		try{
			logger.info("START >>> duplica","UMappa ID[{}]",entity!=null?entity.getId():null);
		 
		 
			return umappaDao.duplica(entity);
			
		} catch (Exception e) {
			logger.error( "duplica  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< duplica" );
		}
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class}, readOnly = false)
	public int updateZoomAndShowCat(UMappa entity) throws Exception {
		try{
			logger.info("START >>> updateZoomAndShowCat","UMappa ID[{}]",entity!=null?entity.getId():null);
		 
		 
			return umappaDao.updateZoomAndShowCat(entity);
			
		} catch (Exception e) {
			logger.error( "updateZoomAndShowCat  >>>>>>>>>>>> " + e, e);
			throw (e);
		} finally {
			logger.info("END <<< updateZoomAndShowCat" );
		}
	}
	
}
