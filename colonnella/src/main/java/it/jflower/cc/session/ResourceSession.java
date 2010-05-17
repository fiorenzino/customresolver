package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.FileUtils;
import it.jflower.cc.par.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

@Named
@SessionScoped
public class ResourceSession
extends SuperSession<Resource>
implements Serializable {

	private static Logger staticLogger = Logger.getLogger(ResourceSession.class);
	
	private static final long serialVersionUID = 1L;

	private static String base = null;
	
	static { 
		base = ResourceSession.class.getClassLoader().getResource("META-INF").getPath();
		base = base.substring(0,base.indexOf("WEB-INF"))
//		+"risorse"+File.separator
		;
		staticLogger.info("Saving resources to: " + base);
	}

	public Resource persist(Resource object) {
		try {
//			File f = new File(base+object.getTipo()+File.separator+object.getNome().replaceAll(" ", "_"));
//			if ( f.exists() || f.isDirectory() )
//				throw new Exception("file could not be written!");
//			FileOutputStream fos = new FileOutputStream(f);
//			fos.write(object.getBytes());
//			fos.close();
			String filename = null;
			if ( "img".equals(object.getTipo()) )
				filename = FileUtils.createImage_(object.getTipo(), object.getId(), object.getBytes());
			else
				filename = FileUtils.createFile_(object.getTipo(), object.getId(), object.getBytes());
			object.setNome(filename);
			object.setId(object.getTipo()+File.separator+object.getNome());
			return object;
		} catch (Exception e) {
			staticLogger.error(e.getMessage(), e);
			return null;
		}
	}

	public Resource find(String tipo, String id) {
		try {
			File f = new File(base+File.separator+tipo+File.separator+id);
			if ( f.exists() && ! f.isDirectory() ) {
				Resource r = new Resource();
				r.setId(id);
				r.setNome(id);
				r.setTipo(tipo);
				return r;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			staticLogger.error(e.getMessage(), e);
			return null;
		}
	}
	
	public Resource fetch(String tipo, String id) {
		try {
			File f = new File(base+File.separator+tipo+File.separator+id);
			if ( f.exists() && ! f.isDirectory() ) {
				Resource r = new Resource();
				r.setId(id);
				r.setNome(id);
				r.setTipo(tipo);
				FileInputStream fis = new FileInputStream(f);
				byte[] bytes = new byte[(int)f.length()];
				for ( int i = 0 ; i < bytes.length ; i++ ) {
					fis.read(bytes);
				}
				r.setBytes(bytes);
				return r;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			staticLogger.error(e.getMessage(), e);
			return null;
		}
	}

	public Resource update(Resource object) {
		try {
			File f = new File(base+File.separator+object.getTipo()+File.separator+object.getId());
			if ( f.exists() && f.isDirectory() )
				throw new Exception("file could not be written!");
			if ( f.exists() ) 
				f.delete();
			f = new File(base+File.separator+object.getTipo()+File.separator+object.getId());
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(object.getBytes());
			fos.close();
			return object;
		} catch (Exception e) {
			staticLogger.error(e.getMessage(), e);
			return null;
		}
	}

	public void delete(Resource object) {
		try {
			File f = new File(base+object.getTipo()+File.separator+object.getId());
			if ( f.exists() && f.isDirectory() )
				throw new Exception("file could not be written!");
			if ( f.exists() ) 
				f.delete();
			return;
		} catch (Exception e) {
			staticLogger.error(e.getMessage(), e);
			return;
		}
	}

	// --- LIST ------------------------------------------

	public List<Resource> getList(Ricerca<Resource> ricerca, int startRow, int pageSize) {
		List<Resource> result = new ArrayList<Resource>();
		try {
//			File f = new File(base+File.separator+ricerca.getOggetto().getTipo());
//			if ( ! f.exists() || ! f.isDirectory() )
//				throw new Exception("directory could not be read!");
			List<String> files = getFiles(ricerca.getOggetto().getTipo());
			if ( startRow > files.size() )
				return result;
			int max = files.size();
			if ( pageSize > 0 )
				max = startRow+pageSize;
			if ( max > files.size() ) {
				max = files.size();
			}
			for ( int i = startRow ; i < max ; i++ ) {
				Resource r = new Resource();
				r.setTipo(ricerca.getOggetto().getTipo());
//				r.setId( files[i].substring( files[i].lastIndexOf("/")+1 ) );
				r.setId( files.get(i) );
				r.setNome( r.getId() );
				result.add(r);
			}
		} catch (Exception e) {
			staticLogger.error(e.getMessage(), e);
		}
		return result;
	}

	public int getListSize(Ricerca<Resource> ricerca) {
		try {
//			File f = new File(base+File.separator+ricerca.getOggetto().getTipo());
//			if ( ! f.exists() || ! f.isDirectory() )
//				throw new Exception("directory could not be read!");
//			return f.list().length;
			return getFiles(ricerca.getOggetto().getTipo()).size();
		} catch (Exception e) {
			staticLogger.error(e.getMessage(), e);
			return 0;
		}
	}	

	
	private List<String> getFiles(String tipo) {
		if ( tipo.equals("img") ) 
			return FileUtils.getImgFiles();
		else if ( tipo.equals("js") ) 
			return FileUtils.getJsFiles();
		if ( tipo.equals("swf") ) 
			return FileUtils.getFlashFiles();
		if ( tipo.equals("css") ) 
			return FileUtils.getCssFiles();
		else
			return new ArrayList<String>();
	}

	@Override
	public EntityManager getEm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<Resource> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getOrderBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEm(EntityManager em) {
		// TODO Auto-generated method stub
		
	}

}

