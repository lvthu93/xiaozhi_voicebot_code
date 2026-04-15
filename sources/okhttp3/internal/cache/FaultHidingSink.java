package okhttp3.internal.cache;

import java.io.IOException;

class FaultHidingSink extends e3 {
    private boolean hasErrors;

    public FaultHidingSink(za zaVar) {
        super(zaVar);
    }

    public void close() throws IOException {
        if (!this.hasErrors) {
            try {
                super.close();
            } catch (IOException e) {
                this.hasErrors = true;
                onException(e);
            }
        }
    }

    public void flush() throws IOException {
        if (!this.hasErrors) {
            try {
                super.flush();
            } catch (IOException e) {
                this.hasErrors = true;
                onException(e);
            }
        }
    }

    public void onException(IOException iOException) {
    }

    public void write(ck ckVar, long j) throws IOException {
        if (this.hasErrors) {
            ckVar.skip(j);
            return;
        }
        try {
            super.write(ckVar, j);
        } catch (IOException e) {
            this.hasErrors = true;
            onException(e);
        }
    }
}
