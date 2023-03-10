package com.example.nebula.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.example.nebula.constant.AttributeEnum;
import com.example.nebula.dto.ImportDto;
import com.example.nebula.dto.graph.*;
import com.example.nebula.exception.GraphExecuteException;
import com.example.nebula.util.NebulaUtil;
import com.example.nebula.vo.AttributeVo;
import com.example.nebula.vo.CommonVo;
import com.example.nebula.vo.DetailSpace;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Descriptin: 图空间
 * @ClassName: SpaceService
 */
@Service
public class SpaceService {

    @Autowired
    GraphCommonService graphCommonService;

    public List<CommonVo> createSpace(GraphCreateSpace graphCreateSpace) {
        //GraphSpace bean = graphSpaceService.queryByChineseName(graphCreateSpace.getSpaceChineseName());
        //if (ObjectUtil.isNotNull(bean)) {
        //    throw new GraphExecuteException("图谱中文名重复");
        //}
        //GraphSpace graphSpace1 = graphSpaceService.queryByName(graphCreateSpace.getSpace());
        //if (ObjectUtil.isNotNull(graphSpace1)) {
        //    throw new GraphExecuteException("图谱标识重复");
        //}
        List list = graphCommonService.executeJson(NebulaUtil.createSpace(graphCreateSpace), CommonVo.class);
        //GraphSpace graphSpace = new GraphSpace();
        //graphSpace.setSpaceName(graphCreateSpace.getSpace());
        //graphSpace.setSpaceChineseName(graphCreateSpace.getSpaceChineseName());
        //graphSpace.setCreateTime(String.valueOf(DateUtil.date()));
        //graphSpace.setCreateUser(StpUtil.getLoginIdAsLong());
        //graphSpace.setVidType(graphCreateSpace.getFixedType() + graphCreateSpace.getSize());
        //graphSpace.setRemark(graphCreateSpace.getComment());
        //graphSpace.setReplicaFactor(graphCreateSpace.getReplicaFactor());
        //graphSpace.setPartitionNum(graphCreateSpace.getPartitionNum());
        //graphSpace.setTypeCode(graphCreateSpace.getTypeCode());
        //graphSpace.setClassifyCode(ClassifyCodeEnum.NEW.name());
        //graphSpaceService.insert(graphSpace);
        ////log.info("图空间mysql保存成功: {}", JSONUtil.toJsonPrettyStr(graphSpace));
        return list;
    }

    public List<DetailSpace> detailSpace(GraphShowAttribute graphShowAttribute) {

        // 所有图空间
        List<AttributeVo> spacesList = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);
        AttributeVo attributeVo1 = spacesList.get(0);

        List<DetailSpace> detailSpaceList = CollectionUtil.newArrayList();

        DetailSpace detailSpace = null;
        for (AttributeVo.DataBean datum : attributeVo1.getData()) {
            int tagsNum = 0;
            int edgesNum = 0;
            int tag = 0;
            detailSpace = new DetailSpace();
            // 查询tgas/edges
            String spaceName = datum.getRow().get(0);
            graphShowAttribute.setSpace(spaceName);
            graphShowAttribute.setAttribute(AttributeEnum.TAGS.name());
            List<AttributeVo> tagsList = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);

            AttributeVo attributeVoTag = tagsList.get(0);
            for (AttributeVo.DataBean attributeVoTagDatum : attributeVoTag.getData()) {
                tagsNum += attributeVoTagDatum.getRow().size();
            }
            graphShowAttribute.setAttribute(AttributeEnum.EDGES.name());
            List<AttributeVo> edgesList = graphCommonService.executeJson(NebulaUtil.showAttributes(graphShowAttribute), AttributeVo.class);
            for (AttributeVo.DataBean dataBean : edgesList.get(0).getData()) {
                edgesNum += dataBean.getRow().size();
            }
            detailSpace.setSpace(spaceName);
            detailSpace.setTagsNum(tagsNum);
            detailSpace.setEdgesNum(edgesNum);
            detailSpaceList.add(detailSpace);
        }

        return detailSpaceList;
    }

    public List<AttributeVo> spaceInfo(String space) {
        return graphCommonService.executeJson(NebulaUtil.showAttributeInfo(GraphShowInfo.builder()
                .attribute("space").attributeName(space).space(space).build()), AttributeVo.class);
    }

}
