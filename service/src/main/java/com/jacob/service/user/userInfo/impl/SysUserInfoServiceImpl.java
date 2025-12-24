package com.jacob.service.user.userInfo.impl;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacob.common.model.user.entity.Comment;
import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.common.utils.DateUtils;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.user.SysUserInfoDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.user.userInfo.SysUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserInfoServiceImpl extends BaseServiceImpl<SysUserInfoDao,SysUserInfo> implements SysUserInfoService {

    @Autowired
    private SysUserInfoDao sysUserInfoDao;

    @Override
    public SqlDao getDao() {
        return sysUserInfoDao;
    }

}
