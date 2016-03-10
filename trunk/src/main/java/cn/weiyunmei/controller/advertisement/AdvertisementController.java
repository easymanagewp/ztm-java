package cn.weiyunmei.controller.advertisement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.weiyunmei.entity.advertisement.Advertisement;
import cn.weiyunmei.support.controller.RestController;

@Controller
@RequestMapping("advertisement")
public class AdvertisementController extends RestController<Advertisement> {

}
