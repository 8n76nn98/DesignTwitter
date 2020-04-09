import java.util.*;

public class Twitter {


    private static int timestamp = 0;
    private static class Tweet {}
    private static class User {}

    private HashMap<Integer,user>userMap = new HashMap<>();

    class tweet {
        private int id;
        private int time;
        private tweet next;

        // 需要传入推文内容（id）和发文时间
        public tweet(int id, int time) {
            this.id = id;
            this.time = time;
            this.next = null;
        }
    }

    // static int timestamp = 0
    class user {
        private int id;
        public Set<Integer> followed;
        // 用户发表的推文链表头结点
        public tweet head;

        public user(int userId) {
            followed = new HashSet<>();
            this.id = userId;
            this.head = null;
            // 关注一下自己
            follow(id);
        }

        public void follow(int userId) {
            followed.add(userId);
        }

        public void unfollow(int userId) {
            // 不可以取关自己
            if (userId != this.id)
                followed.remove(userId);
        }

        public void post(int tweetId) {
            tweet twt = new tweet(tweetId, timestamp);
            timestamp++;
            // 将新建的推文插入链表头
            // 越靠前的推文 time 值越大
            twt.next = head;
            head = twt;
        }
    }

    /* 还有那几个 API 方法 */
    public void postTweet(int userId, int tweetId) {
        if(!userMap.containsKey(userId))
        {
            userMap.put(userId,new user(userId));
        }
        user u = userMap.get(userId);
        u.post(tweetId);
    }

    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followeeId))
        {
            user u = new user(followeeId);
            userMap.put(followeeId,u);
        }
        if(!userMap.containsKey(followeeId))
        {
            user u = new user(followeeId);
            userMap.put(followeeId,u);
        }
        userMap.get(followeeId).follow(followeeId);
    }
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followeeId))
        {
            user u = new user(followerId);
            u.unfollow(followeeId);
        }
    }
    public List<Integer> getNewsFeed(int userId)
    {
        List<Integer>result = new ArrayList<>();
        if(!userMap.containsKey(userId))
        {
            return result;
        }
        Set<Integer>users = userMap.get(userId).followed;
        PriorityQueue<tweet>pq = new PriorityQueue<>(users.size(),
                (a,b)->(b.time-a.time));
        for(int id:users)
        {
            tweet twt = userMap.get(id).head;
            if(twt == null) continue;;
            pq.add(twt);
        }
        while(!pq.isEmpty())
        {
            if(result.size()==10)break;
            tweet twt = pq.poll();
            result.add(twt.id);
            if(twt.next !=null)
            {
                pq.add(twt.next);
            }
        }
        return result;
    }
}
