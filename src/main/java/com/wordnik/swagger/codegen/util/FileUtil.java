package com.wordnik.swagger.codegen.util;

import com.wordnik.swagger.exception.CodeGenerationException;

import java.io.*;

/**
 * User: deepakmichael
 * Date: 03/08/11
 * Time: 12:02 AM
 */
public class FileUtil {

    public static void createOutputDirectories(String classLocation, String fileExtension) {
        File outputLocation = new File(classLocation);
        outputLocation.mkdirs(); //make folder if necessary
        //clear contents
        clearFolder(classLocation, fileExtension);

    }

    public static boolean deleteFile(String sFilePath) {
        File oFile = new File(sFilePath);
        if (!oFile.exists()) {
            return false;
        }
        return oFile.delete();
    }

    // Clears the folder of the files with extension
    public static void clearFolder(String strFolder, final String strExt) {
        File fLogDir = new File(strFolder);
        File[] fLogs = fLogDir.listFiles(new FilenameFilter() {
            public boolean accept(File fDir, String strName) {
                return (strName.endsWith(strExt));
            }
        });

        for (int i = 0; i < fLogs.length; i++) {
            deleteFile(fLogs[i].getAbsolutePath());
        }
    }

    public static void copyDirectory(File srcPath, File dstPath) {
        if (srcPath.isDirectory()) {
            if (!dstPath.exists()) {
                dstPath.mkdir();
            }

            String files[] = srcPath.list();
            for (int i = 0; i < files.length; i++) {
                copyDirectory(new File(srcPath, files[i]), new File(dstPath, files[i]));
            }
        } else {
            if (!srcPath.exists()) {
                throw new CodeGenerationException("Source folder does not exist");
            } else {
                try {
                    InputStream in = new FileInputStream(srcPath);
                    OutputStream out = new FileOutputStream(dstPath);

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                } catch (IOException e) {
                    throw new CodeGenerationException("Copy directory operation failed");
                }
            }
        }
    }
}