package com.app.menta.web;

import com.app.menta.dao.PostureDao;
import com.app.menta.dao.ResultsDao;
import com.app.menta.dto.MandalaDataDto;
import com.app.menta.dto.MandalaElementDto;
import com.app.menta.dto.MandalaRequestDto;
import com.app.menta.dto.TheneRequestDto;
import com.app.menta.entity.Posture;
import com.app.menta.entity.Results;
import com.app.menta.service.EntryDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Webコントローラ
 *
 */


@Controller
@Slf4j
public class WebThemeController {

    @Autowired
    EntryDbService entryDbService;

    @Autowired
    private ResultsDao resultsDao;
    @Autowired
    private PostureDao postureDao;

    /**
     * 課題ホーム画面のコントローラ
     *
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String get(Model model) {
        // 登録されているタイトルを取得
        List<Results> resulty = resultsDao.selectAll();
        model.addAttribute("resultys", resulty);
        return "home";
    }

    @GetMapping("/home/{title}")
    public String deleteHome(@PathVariable String title, Model model) {
        entryDbService.delete(title);
        List<Results> resulty = resultsDao.selectAll();
        model.addAttribute("resultys", resulty);
        return "home";
    }

    @PostMapping("/home")
    public String post(String searchName,MandalaRequestDto request,Model model) {
        if (searchName != null) {
            List<Results> result = resultsDao.select(searchName);
            model.addAttribute("resultys", result);
        } else {
            // 登録画面からの要求時はマンダラチャートを登録後、画面にタイトルを出力させる為、タイトルを取得
            entryDbService.excute(request);
            List<Results> resulty = resultsDao.selectAll();
            model.addAttribute("resultys", resulty);
        }

        return "home";
    }

    /**
     * マンダラチャート画面新規登録時のコントローラ
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String newRegister(Model model) {

        // マンダラチャート文の空の枠を作成
        MandalaRequestDto req = new MandalaRequestDto();
        List<MandalaElementDto> elements = createInitMandalaElement();
        req.setElements(elements);
        model.addAttribute("request", req);
        return "register";

    }

    /**
     * マンダラチャート初期データ作成(ホントは別クラスにしたい)
     * @return 初期表示用マンダラデータ
     */
    private List<MandalaElementDto> createInitMandalaElement() {
        List<MandalaElementDto> elements = new ArrayList<>();
        // 親、子の番号を振りながら3×3の要素を×3生成する
        for(int i = 1; i < 10 ; i++) {
            MandalaElementDto element = new MandalaElementDto();
            element.setPrntRowNumber(String.valueOf(i));
            List<MandalaDataDto> datas = new ArrayList<>();
            for(int y = 1; y < 10 ; y++) {
                MandalaDataDto data = new MandalaDataDto();
                data.setRowNumber(String.valueOf(y));
                datas.add(data);
            }
            element.setDataList(datas);
            elements.add(element);
        }
        return elements;
    }

    /**
     * マンダラチャート画面リンク押下時(既に登録済)のコントローラ
     * @param id 対象のマンダラチャートのId
     * @param model
     * @return
     */
    @GetMapping("/register/{id}")
    public String register(@PathVariable String id,Model model) {

        // 表示用にタイトル取得
        Results results = resultsDao.selectById(id);
        model.addAttribute("title", results.getResults());
        // idより全てのデータを取得
        List<Posture> allDataList = postureDao.selectAllDataById(id);
        // 表示用にデータ生成
        MandalaRequestDto req = new MandalaRequestDto();
        List<MandalaElementDto> elements = new ArrayList<>();
        if(allDataList.size() != 81){
            //適正サイズではない場合は初期化(ゴミデータがない限り分岐しない)
            elements = createInitMandalaElement();
            // ゴミデータ対処なので、強引にタイトルセット
            elements.get(4).getDataList().get(4).setInputValue(results.getResults());
        }else {
            // 親の３×３要素分ループ
            for (int i = 1; i < 10; i++) {
                MandalaElementDto element = new MandalaElementDto();
                // ラムダ式はFInalである必要がある為、変数へセット
                int index = i;
                // 親の番号をセット
                element.setPrntRowNumber(String.valueOf(i));
                List<MandalaDataDto> datas = new ArrayList<>();
                // 子要素の３×３を生成(親の表示位置のデータを取得→子要素表示順位並び変えてセット)
                allDataList.stream()
                        .filter(d -> d.getPosition() == index)
                        .sorted(Comparator.comparingInt(Posture::getPosture_position))
                        .forEach(d -> {
                            // 表示する値をセット
                            MandalaDataDto data = new MandalaDataDto();
                            data.setRowNumber(String.valueOf(d.getPosture_position()));
                            data.setInputValue(d.getPosture());
                            datas.add(data);
                        });
                element.setDataList(datas);
                elements.add(element);
            }
        }

        req.setElements(elements);
        req.setId(id);
        model.addAttribute("request", req);

        return "register";
    }
}
