package by.giava.base.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.base.common.util.FileUtils;
import by.giava.base.pojo.Resource;

@Named
@Stateless
@LocalBean
public class ResourceRepository extends AbstractRepository<Resource> {

	private static final long serialVersionUID = 1L;

	private static String base = null;

	@Inject
	EntityManager em;

	static {
		base = ResourceRepository.class.getClassLoader()
				.getResource("META-INF").getPath();
		logger.info("base: " + base);
		if (base != null && base.contains("WEB-INF"))
			base = base.substring(0, base.indexOf("WEB-INF"));

		// +"risorse"+File.separator

		logger.info("Saving resources to: " + base);
	}

	@Override
	public Resource persist(Resource object) {
		try {
			// File f = new
			// File(base+object.getTipo()+File.separator+object.getNome().replaceAll(" ",
			// "_"));
			// if ( f.exists() || f.isDirectory() )
			// throw new Exception("file could not be written!");
			// FileOutputStream fos = new FileOutputStream(f);
			// fos.write(object.getBytes());
			// fos.close();
			String filename = null;
			if ("img".equals(object.getTipo()))
				filename = FileUtils.createImage_(object.getTipo(),
						object.getNome(), object.getBytes());
			else
				filename = FileUtils.createFile_(object.getTipo(),
						object.getNome(), object.getBytes());
			object.setNome(filename);
			object.setId(object.getTipo() + File.separator + object.getNome());
			return object;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	public Resource find(String tipo, String id) {
		try {
			File f = new File(base + File.separator + tipo + File.separator
					+ id);
			if (f.exists() && !f.isDirectory()) {
				Resource r = new Resource();
				r.setId(id);
				r.setNome(id);
				r.setTipo(tipo);
				return r;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	public Resource fetch(String tipo, String id) {
		try {
			File f = new File(base + File.separator + tipo + File.separator
					+ id);
			if (f.exists() && !f.isDirectory()) {
				Resource r = new Resource();
				r.setId(id);
				r.setNome(id);
				r.setTipo(tipo);
				FileInputStream fis = new FileInputStream(f);
				byte[] bytes = new byte[(int) f.length()];
				for (int i = 0; i < bytes.length; i++) {
					fis.read(bytes);
				}
				r.setBytes(bytes);
				return r;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	public Resource updateResource(Resource object) {
		try {
			File f = new File(base + File.separator + object.getTipo()
					+ File.separator + object.getId());
			if (f.exists() && f.isDirectory())
				throw new Exception("file could not be written!");
			if (f.exists())
				f.delete();
			f = new File(base + File.separator + object.getTipo()
					+ File.separator + object.getId());
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(object.getBytes());
			fos.close();
			return object;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	public void delete(Resource object) {
		try {
			File f = new File(base + object.getTipo() + File.separator
					+ object.getId());
			if (f.exists() && f.isDirectory())
				throw new Exception("file could not be written!");
			if (f.exists())
				f.delete();
			return;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return;
		}
	}

	// --- LIST ------------------------------------------
	@Override
	public List<Resource> getList(Search<Resource> ricerca, int startRow,
			int pageSize) {
		List<Resource> result = new ArrayList<Resource>();
		try {
			// File f = new
			// File(base+File.separator+ricerca.getOggetto().getTipo());
			// if ( ! f.exists() || ! f.isDirectory() )
			// throw new Exception("directory could not be read!");
			List<String> files = getFiles(ricerca.getObj().getTipo(), ricerca
					.getObj().getNome());
			if (startRow > files.size())
				return result;
			int max = files.size();
			if (pageSize > 0)
				max = startRow + pageSize;
			if (max > files.size()) {
				max = files.size();
			}
			for (int i = startRow; i < max; i++) {
				Resource r = new Resource();
				r.setTipo(ricerca.getObj().getTipo());
				// r.setId( files[i].substring( files[i].lastIndexOf("/")+1 ) );
				r.setId(files.get(i));
				r.setNome(r.getId());
				result.add(r);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return result;
	}

	@Override
	public int getListSize(Search<Resource> ricerca) {
		try {
			// File f = new
			// File(base+File.separator+ricerca.getOggetto().getTipo());
			// if ( ! f.exists() || ! f.isDirectory() )
			// throw new Exception("directory could not be read!");
			// return f.list().length;
			return getFiles(ricerca.getObj().getTipo(),
					ricerca.getObj().getNome()).size();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return 0;
		}
	}

	private List<String> getFiles(String tipo, String nameLike) {
		List<String> resources = new ArrayList<String>();
		if (tipo == null || tipo.isEmpty())
			return resources;
		if (tipo.equals("img")) {
			resources = FileUtils.getImgFiles();
		} else if (tipo.equals("js"))
			resources = FileUtils.getJsFiles();
		else if (tipo.equals("swf"))
			resources = FileUtils.getFlashFiles();
		else if (tipo.equals("css")) {
			resources = FileUtils.getCssFiles();
		} else if (tipo.equals("docs")) {
			resources = FileUtils.getPdfFiles();
		}
		if (resources == null || resources.size() == 0 || nameLike == null
				|| nameLike.equals(""))
			return resources;
		List<String> filteredFiles = new ArrayList<String>();
		for (String filename : resources) {
			if (filename.toUpperCase().contains(nameLike.toUpperCase()))
				filteredFiles.add(filename);
		}
		return filteredFiles;
	}

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	protected String getDefaultOrderBy() {
		return "nome";
	}

}
