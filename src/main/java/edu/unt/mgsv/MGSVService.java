package edu.unt.mgsv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@WebService
public class MGSVService {

	/**
	 * Uploads the given urls to the mGSV server and returns the id for the
	 * upload.
	 * 
	 * @param synteny
	 *            url
	 * @param annotation
	 *            url *optional (Leave null if not available)
	 * @param email
	 *            email address (Leave null if not available)
	 * @return id for the upload
	 * @throws Exception
	 */
	public String uploadURL(String synteny, String annotation, String email) throws Exception {
		if (synteny == null)
			return null;

		File syntenyFile = download(synteny);
		File annotationFile = null;
		if (annotation != null) {
			annotationFile = download(annotation);
		}

		String result = makeRequest(syntenyFile, annotationFile, email);

		return result;
	}

	/**
	 * Upload the synteny, annotation data given as string.
	 * 
	 * @param synteny
	 *            Synteny data
	 * @param annotation
	 *            Annotation data *optional (Leave null if not available)
	 * @param email
	 *            Email address *optional (Leave null if not available)
	 * @return ID of the upload
	 * @throws Exception
	 *             Passed as it is
	 */
	public String uploadData(String synteny, String annotation, String email) throws Exception {
		// Decode to file

		if (synteny == null)
			return null;

		File syntenyFile = toFile(synteny);
		File annotationFile = null;
		if (annotation != null) {
			annotationFile = toFile(annotation);
		}

		String result = makeRequest(syntenyFile, annotationFile, email);

		return result;
	}

	private String makeRequest(File synteny, File annotation, String email) throws Exception {

		/**
		 * Makes a request to the mGSV server with files. Both the method uses
		 * this method finally to make the actual request. Returns the id.
		 */

		HttpPost httppost = new HttpPost(config.getProperty("mgsv_upload_url"));
		MultipartEntity multipart = new MultipartEntity();

		FileBody syntenyBody = new FileBody(synteny);
		multipart.addPart("file0", syntenyBody);

		if (annotation != null) {
			FileBody annotationBody = new FileBody(annotation);
			multipart.addPart("file1", annotationBody);
		} else {
			multipart.addPart("file1", new StringBody(""));
		}

		if (email != null) {
			multipart.addPart("email", new StringBody(email));
		}

		FormBodyPart part = new FormBodyPart("pro", new StringBody("Submit"));

		multipart.addPart(part);
		httppost.setEntity(multipart);

		HttpResponse response = httpclient.execute(httppost);
		HttpEntity responseEntity = response.getEntity();

		int status = response.getStatusLine().getStatusCode();
		if (status != 302)
			return null;

		String locationHeader = response.getHeaders("Location")[0].getValue();
		if (locationHeader != null) {
			locationHeader = locationHeader.replaceAll("\\Qsummary.php?session_id=\\E", "");
		}
		EntityUtils.consume(responseEntity);

		return locationHeader;
	}

	private File toFile(String data) throws Exception {
		File file = File.createTempFile("mgsv", ".gz");
		GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(file));
		out.write(data.getBytes("UTF-8"));
		out.close();
		return file;
	}

	private File download(String url) throws Exception {
		/**
		 * Downloads the given url to a temp file. File is compressed using
		 * Gzip. Returns the temp file. Caller is responsible for its deletion
		 * if required.
		 */
		File file = File.createTempFile("mgsv", ".gz");
		GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(file));
		URLConnection connection = new URL(url).openConnection();

		InputStream in = connection.getInputStream();
		int i;

		while ((i = in.read()) != -1) {
			out.write(i);
		}

		in.close();
		out.close();
		return file;
	}

	Properties	config		= new Properties();
	HttpClient	httpclient	= new DefaultHttpClient();

	private void execute() throws Exception {
		/**
		 * Loads the configuration file and publishes itself.
		 */
		config.load(new FileInputStream("config.properties"));
		Endpoint.publish(config.getProperty("ws_publish_url"), this);
		System.out.println("Published at : " + config.getProperty("ws_publish_url"));
	}

	public static void main(String[] args) throws Exception {
		MGSVService service = new MGSVService();
		service.execute();
	}

}
