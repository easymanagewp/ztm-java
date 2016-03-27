package cn.weiyunmei.controller.advertisement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.weiyunmei.entity.advertisement.AdvertisementDataType;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("/advertisement/data/type")
public class AdvertisementDataTypeController extends RestController<AdvertisementDataType> {

}
