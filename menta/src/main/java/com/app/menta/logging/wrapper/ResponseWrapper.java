package com.app.menta.logging.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class ResponseWrapper extends HttpServletResponseWrapper {

    private TeeServletOutputStream mTeeOutputStream;

    private ByteArrayOutputStream mByteArrayOutputStream;


    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        mByteArrayOutputStream = new ByteArrayOutputStream();
        mTeeOutputStream = new TeeServletOutputStream(super.getResponse().getOutputStream(),mByteArrayOutputStream);
    }

    @Override
    public PrintWriter getWriter()throws IOException {
        return super.getResponse().getWriter();
    }

    @Override
    public ServletOutputStream getOutputStream(){
        return mTeeOutputStream;
    }

    public String getResponseBody()throws UnsupportedEncodingException{
        return mByteArrayOutputStream.toString("UTF-8");
    }




    private static class TeeOutputStream extends OutputStream {

        protected OutputStream mChainStream;
        protected OutputStream mTeeStream;


        public TeeOutputStream(OutputStream chainStream,OutputStream teeStream) {
            this.mChainStream = chainStream;
            this.mTeeStream = teeStream;
        }

        @Override
        public void write(int b) throws IOException {
            mChainStream.write(b);
            mTeeStream.write(b);
            mTeeStream.flush();
        }

        @Override
        public void close() throws IOException {
            flush();
            mChainStream.close();
            mTeeStream.close();
        }

        @Override
        public void flush() throws IOException {
            mChainStream.flush();
        }
    }

    public class TeeServletOutputStream extends ServletOutputStream {

        private final TeeOutputStream targetStream;

        public TeeServletOutputStream(OutputStream one,OutputStream two) {
            targetStream = new TeeOutputStream(one,two);
        }

        @Override
        public void write(int b) throws IOException {
            this.targetStream.write(b);
        }

        @Override
        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {
        }
    }

}
