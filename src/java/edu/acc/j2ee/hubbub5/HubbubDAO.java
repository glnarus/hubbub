package edu.acc.j2ee.hubbub5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class HubbubDAO {
    private String lastError;
    
    private Connection CONN;

    public HubbubDAO(String jdbcUrl) {
        try {
            CONN = DriverManager.getConnection(jdbcUrl);
            lastError = null;
        } catch (SQLException sqle) {
            lastError = sqle.getMessage();
        }
    }
    
    public String getLastError() { return lastError; }
    
    protected void addPost(Post post) {
        String sql = "INSERT INTO POSTS (content, authorid, postdate) VALUES (?,?,?)";
        PreparedStatement pstat = null;
        try {
            pstat = CONN.prepareStatement(sql);
            pstat.setString(1, post.getContent());
            pstat.setInt(2, post.getAuthor().getId());
            pstat.setDate(3, new java.sql.Date(post.getPostDate().getTime()));
            pstat.executeUpdate();
            lastError = null;
        } catch (SQLException sqle) {
            lastError = sqle.getMessage();
        } finally {
            if (pstat != null)
                try {
                    pstat.close();
                } catch (SQLException sqle) {}
        }
    }

    public void addPost(String content, User user) {
        Post post = new Post(content, new Date(), user);
        addPost(post);
    }

    public List<Post> getSortedPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM POSTS ORDER BY postdate DESC";
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = CONN.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                Post p = new Post(
                        rs.getString("content"),
                        new Date(rs.getDate("postdate").getTime()),
                        getUserById(rs.getInt("authorid")),
                        rs.getInt("id")
                );
                posts.add(p);
            }
            lastError = null;
        } catch (SQLException sqle) {
            lastError = sqle.getMessage();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException sqle) {}
            if (stat != null)
                try {
                    stat.close();
                } catch (SQLException sqle) {}            
        }
        return posts;   
    }
    
    public User getUserById(int id) {
        String sql = "SELECT * FROM USERS WHERE id = " + id;
        Statement stat = null;
        ResultSet rs = null;
        User user = null;
        try {
            stat = CONN.createStatement();
            rs = stat.executeQuery(sql);
            if (rs.next()) {
                user = new User(
                        rs.getString("username"),                        
                        rs.getInt("id"),
                        rs.getInt("profileid"));
                addUserProfileInfo(user);
            }
            lastError = null;
        } catch (SQLException sqle) {
            lastError = sqle.getMessage();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException sqle) {}
            if (stat != null)
                try {
                    stat.close();
                } catch (SQLException sqle) {}            
        }
        return user;
    }
    
    //this does not need to lookup anything in the
    //User table, it only gets stuff from the Profiles table
    public void addUserProfileInfo(User user) {
        String sql = "SELECT * FROM PROFILES WHERE id = " + user.getProfileId();
        Statement stat = null;
        ResultSet rs = null;
        if (user != null) {
            try {
                stat = CONN.createStatement();
                rs = stat.executeQuery(sql);
                if (rs.next()) {
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("firstname"));
                    user.setLastName(rs.getString("lastname"));
                    user.setZipCode(rs.getString("zip"));
                    user.setJoinDate(new Date(rs.getDate("joindate").getTime()));                                                
                }
                lastError = null;
            } catch (SQLException sqle) {
                lastError = sqle.getMessage();
            } finally {
                if (rs != null)
                    try {
                        rs.close();
                    } catch (SQLException sqle) {}
                if (stat != null)
                    try {
                        stat.close();
                    } catch (SQLException sqle) {}            
            }
        }
    }    

    public User authenticate(String userName, String password) {
        User user = null;
        String sql = "SELECT * FROM USERS WHERE username = '%s' AND password = '%s'";
        sql = String.format(sql, userName, password);
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = CONN.createStatement();
            rs = stat.executeQuery(sql);
            if (rs.next()) {
                user = new User(
                        rs.getString("username"),                        
                        rs.getInt("id"),
                        rs.getInt("profileid"));
                addUserProfileInfo(user);               
            }
            lastError = null;
        } catch (SQLException sqle) {
            lastError = sqle.getMessage();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException sqle) {}
            if (stat != null)
                try {
                    stat.close();
                } catch (SQLException sqle) {}
        }
        return user;
    }
    
    public int register(RegistrationBean bean) {
        String sql = "INSERT INTO USERS (username,password)";
        sql += " VALUES (?,?)";
        PreparedStatement pstat = null;
        Statement stat = null;
        ResultSet rs = null;
        int id = 0;
        int pid = 0;
        try {
            pstat = CONN.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstat.setString(1, bean.getUserName());
            pstat.setString(2, bean.getPassword1());
            pstat.executeUpdate();
            rs = pstat.getGeneratedKeys();
            if (rs.next())
                id = rs.getInt(1);
            //User is created and we have an id, so now let's make the Profile
            //First close out pstat and rs since we will use them for profiles 
            //now
            pstat.close();            
            sql = "INSERT INTO PROFILES (firstname,lastname,email,zip,userid)";
            sql += " VALUES (?,?,?,?,?)";
            pstat = CONN.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstat.setString(1,bean.getFirstName());
            pstat.setString(2,bean.getLastName());
            pstat.setString(3,bean.getEmail());
            pstat.setString(4,bean.getZipCode());
            pstat.setInt(5,id);
            pstat.executeUpdate();
            rs = pstat.getGeneratedKeys();
            if (rs.next()) {                
                pid = rs.getInt(1);
                //insert the profile ID into the user dbase
                sql = String.format(
                        "UPDATE USERS SET profileId=%d WHERE id=%d", pid, id);
                stat = CONN.createStatement();
                stat.executeUpdate(sql);
                lastError = null;
            }                
            lastError = null;
        } catch (SQLException sqle) {
            lastError = sqle.getMessage();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException sqle) {}
            if (pstat != null)
                try {
                    pstat.close();
                } catch (SQLException sqle) {}
            if (stat != null)
                try { stat.close();}                
                catch (SQLException sqle) {}
        }
        return id;
    }
   
        
    public void close() {
        if(CONN != null)
            try {
                CONN.close();
            } catch (SQLException sqle) {}
    }
}
