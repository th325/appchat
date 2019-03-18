/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

/**
 *
 * @author Huynh
 */
public class FileInfoClass implements java.io.Serializable {
    public static final long serialVersionUID = 1L;
        public String filename;
        public long fileSize;
        public int piecesOfFile;
        public int lastByteLength;
        public String status;
        
        public void setPiecesOfFile(int piecesOfFile){
            this.piecesOfFile=piecesOfFile;
        }
        public void setFilename(String filename){
            this.filename=filename;
        }
        public void setLastByteLength(int lastByteLength){
            this.lastByteLength=lastByteLength;
        }
        public void setFileSize(long fileSize){
            this.fileSize=fileSize;
        }
        public void setStatus(String status){
            this.status=status;
        }
        
        
        public int getPiecesOfFile(){
            return piecesOfFile;
        }
        public String getFilename(){
            return filename;
        }
        public int getLastByteLength(){
            return lastByteLength;
        }
        public long getFileSize(){
            return fileSize;
        }
        public String getStatus(){
            return status;
        }
}
