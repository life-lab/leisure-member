package com.github.hicolors.leisure.member.application.repository.listeners;

import com.github.hicolors.leisure.common.jpa.customiz.listener.AbstractListener;
import com.github.hicolors.leisure.common.utils.JsonUtils;
import com.github.hicolors.leisure.member.application.amqp.MqProperties;
import com.github.hicolors.leisure.member.model.persistence.Member;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.MergeEvent;
import org.hibernate.event.spi.MergeEventListener;
import org.hibernate.event.spi.PersistEvent;
import org.hibernate.event.spi.PersistEventListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * PlatformListener
 *
 * @author weichao.li (liweichao0102@gmail.com)
 * @date 2018/11/3
 */
@Component
@Slf4j
public class MemberListener extends AbstractListener implements PersistEventListener, MergeEventListener {

    @Autowired
    private MqProperties mqProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Async
    public void onMerge(MergeEvent event) throws HibernateException {
        if (event.getResult() instanceof Member) {
            log.info("member 信息变更，发送 rabbit mq，member: [{}]", ((Member) event.getResult()).getId());
            rabbitTemplate.convertAndSend(mqProperties.getMemberTopicExchange(),mqProperties.getMemberRoutingKey(), ((Member) event.getResult()).getId());
        }

    }

    @Override
    public void onMerge(MergeEvent event, Map copiedAlready) throws HibernateException {

    }

    @Override
    public void onPersist(PersistEvent event) throws HibernateException {
        if (event.getObject() instanceof Member) {

        }
    }

    @Override
    public void onPersist(PersistEvent event, Map createdAlready) throws HibernateException {
    }
}