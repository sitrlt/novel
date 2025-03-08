package com.example;

import java.util.*;

// 用户借阅历史类
class UserBorrowHistory {
    private int userId;
    private List<Integer> borrowedBookIds;

    public UserBorrowHistory(int userId, List<Integer> borrowedBookIds) {
        this.userId = userId;
        this.borrowedBookIds = borrowedBookIds;
    }

    public int getUserId() {
        return userId;
    }

    public List<Integer> getBorrowedBookIds() {
        return borrowedBookIds;
    }
}

// 基于用户的协同过滤推荐器
class UserBasedCollaborativeFilteringRecommender {
    public List<Integer> recommendBooks(int targetUserId, List<UserBorrowHistory> allHistories, int topN) {
        // 找到目标用户的借阅历史
        UserBorrowHistory targetUserHistory = null;
        for (UserBorrowHistory history : allHistories) {
            if (history.getUserId() == targetUserId) {
                targetUserHistory = history;
                break;
            }
        }
        if (targetUserHistory == null) {
            return new ArrayList<>();
        }

        // 计算用户之间的相似度
        Map<Integer, Double> userSimilarities = new HashMap<>();
        for (UserBorrowHistory otherHistory : allHistories) {
            if (otherHistory.getUserId() != targetUserId) {
                double similarity = calculateSimilarity(targetUserHistory.getBorrowedBookIds(), otherHistory.getBorrowedBookIds());
                userSimilarities.put(otherHistory.getUserId(), similarity);
            }
        }

        // 按相似度排序
        List<Map.Entry<Integer, Double>> sortedSimilarities = new ArrayList<>(userSimilarities.entrySet());
        sortedSimilarities.sort(Map.Entry.<Integer, Double>comparingByValue().reversed());

        // 推荐书籍
        Set<Integer> recommendedBookIds = new HashSet<>();
        for (Map.Entry<Integer, Double> entry : sortedSimilarities) {
            int similarUserId = entry.getKey();
            UserBorrowHistory similarUserHistory = null;
            for (UserBorrowHistory history : allHistories) {
                if (history.getUserId() == similarUserId) {
                    similarUserHistory = history;
                    break;
                }
            }
            if (similarUserHistory != null) {
                for (int bookId : similarUserHistory.getBorrowedBookIds()) {
                    if (!targetUserHistory.getBorrowedBookIds().contains(bookId)) {
                        recommendedBookIds.add(bookId);
                        if (recommendedBookIds.size() >= topN) {
                            break;
                        }
                    }
                }
            }
            if (recommendedBookIds.size() >= topN) {
                break;
            }
        }

        return new ArrayList<>(recommendedBookIds);
    }

    private double calculateSimilarity(List<Integer> list1, List<Integer> list2) {
        Set<Integer> set1 = new HashSet<>(list1);
        Set<Integer> set2 = new HashSet<>(list2);
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return (double) intersection.size() / (set1.size() + set2.size() - intersection.size());
    }
}

// 测试代码
public class test2 {
    public static void main(String[] args) {
        // 初始化用户借阅历史
        List<UserBorrowHistory> allHistories = new ArrayList<>();
        allHistories.add(new UserBorrowHistory(1, Arrays.asList(1, 2, 3)));
        allHistories.add(new UserBorrowHistory(2, Arrays.asList(2, 3, 4)));
        allHistories.add(new UserBorrowHistory(3, Arrays.asList(3, 4, 5)));

        // 创建推荐器
        UserBasedCollaborativeFilteringRecommender recommender = new UserBasedCollaborativeFilteringRecommender();

        // 进行推荐
        List<Integer> recommendedBookIds = recommender.recommendBooks(3, allHistories, 2);

        // 输出推荐结果
        System.out.println("为用户推荐的书籍 ID：");
        for (int bookId : recommendedBookIds) {
            System.out.println(bookId);
        }
    }
}