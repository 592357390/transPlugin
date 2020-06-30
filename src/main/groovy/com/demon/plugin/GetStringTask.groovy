package com.demon.plugin


import com.demon.plugin.translate.lang.LANG
import com.demon.plugin.translate.querier.Querier
import com.demon.plugin.translate.trans.AbstractTranslator
import com.demon.plugin.translate.trans.impl.GoogleTranslator
import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.xml.sax.SAXException

import javax.inject.Inject
import javax.xml.parsers.ParserConfigurationException

public class GetStringTask extends DefaultTask {

    @Input
    List<String> targetLanguage = new ArrayList<>()

    def oriPath
    def data = [:]

    @Inject
    GetStringTask(String path) {
        this.oriPath = path
    }

    @TaskAction
    public void readStringXmlInfo() {
        def stringXmlPath = oriPath + "\\src\\main\\res\\values\\strings.xml"
        println("need trans file path: " + stringXmlPath)
        try {
            def xmlSlurper = new XmlSlurper()
            def response = xmlSlurper.parse(new File(stringXmlPath))

            //获取keys,values
            response.children().each {
                println(it)
                data.put(it.@name, it)
            }

            targetLanguage.each {
                println(it)
                def targetLang
                if (it.toUpperCase() == LANG.ZH.toString()) {
                    targetLang = LANG.ZH
                }
                if (it.toUpperCase() == LANG.EN.toString()) {
                    targetLang = LANG.EN
                }
                if (it.toUpperCase() == LANG.JP.toString()) {
                    targetLang = LANG.JP
                }
                if (it.toUpperCase() == LANG.FRA.toString()) {
                    targetLang = LANG.FRA
                }
                if (it.toUpperCase() == LANG.KOR.toString()) {
                    targetLang = LANG.KOR
                }
                if (it.toUpperCase() == LANG.DE.toString()) {
                    targetLang = LANG.DE
                }
                if (it.toUpperCase() == LANG.RU.toString()) {
                    targetLang = LANG.RU
                }

                if (targetLang == null) {
                    return
                }
                def transData = [:]
                data.each {
                    Querier<AbstractTranslator> quer = new Querier<>();
                    quer.setParams(LANG.AUTO, targetLang, it.value.toString());

                    quer.attach(new GoogleTranslator());

                    List<String> resultTrans = quer.execute();
                    for (String str : resultTrans) {
                        println(str)
                        transData.put(it.key, str)
                    }
                }
                def targetPath = oriPath + "\\src\\main\\res\\values-" + it.toLowerCase()
                println(targetPath)
                def file = new File(targetPath)
                file.mkdirs()

                def st1 = new StringWriter()
                MarkupBuilder mb1 = new MarkupBuilder(st1);
                mb1.resources {
                    transData.each {
                        string(name: it.key, it.value)
                    }
                }

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(targetPath, "strings.xml"), false), "UTF-8"))
                bw.write(st1.getBuffer().toString())
                bw.newLine();
                bw.flush()
                bw.close()

            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace()
        }

    }


}