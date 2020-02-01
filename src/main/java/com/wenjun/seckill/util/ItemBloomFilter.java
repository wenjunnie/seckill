package com.wenjun.seckill.util;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: wenjun
 * @Date: 2020/1/24 16:05
 */
@Component
public class ItemBloomFilter {
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),10);

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            bloomFilter.put(i);
        }
    }

    public boolean contains(int i) {
        return bloomFilter.mightContain(i);
    }
}
