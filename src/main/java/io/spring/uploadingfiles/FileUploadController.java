package io.spring.uploadingfiles;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController
{

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String provideUploadInfo()
    {
        // return "You can upload a file by posting to this same URL.";
        return "/upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file)
    {
        System.out.println("handling file upload...");

        if (!file.isEmpty())
        {
            try
            {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("uploaded", name +
                                "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + " into " + name +
                        "-uploaded in directory /uploaded!";
            }
            catch (Exception e)
            {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        }
        else
        {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

}