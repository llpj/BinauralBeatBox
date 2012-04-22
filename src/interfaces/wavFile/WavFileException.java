package interfaces.wavFile;

public class WavFileException extends Exception {
	/**
	 * ID nachtraeglich generiert, nicht im originalen File enthalten.
	 */
	private static final long serialVersionUID = -4900318673188284717L;

	public WavFileException() {
		super();
	}

	public WavFileException(String message) {
		super(message);
	}

	public WavFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public WavFileException(Throwable cause) {
		super(cause);
	}
}
