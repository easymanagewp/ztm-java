package cn.weiyunmei.controller.advertisement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.weiyunmei.entity.advertisement.AdvertisementType;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("/advertisement/type")
public class AdvertisementTypeController extends RestController<AdvertisementType> {

}
