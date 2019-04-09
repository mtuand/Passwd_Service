package com.braincorp.project.controller;


import com.braincorp.project.model.Groups;
import com.braincorp.project.model.Users;
import com.braincorp.project.repository.GroupsRepository;
import com.braincorp.project.repository.UsersRepository;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/service")
public class Controller {
    private UsersRepository usersRepository;
    private GroupsRepository groupsRepository;

    public Controller(UsersRepository usersRepository, GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/users")
    public List<Users> getUsers(){
        return this.usersRepository.findAll();
    }

    @GetMapping("/users/{uid}")
    public Users getByUid(@PathVariable("uid") int uid){
        Users user = this.usersRepository.findByuid(uid);
        if(user == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        return user;
    }

    @GetMapping("/users/query?name={nq}&uid={uq}&gid={gq}&comment={cq}&home={hq}&shell={sq}")
    public List<Users> getUsersFiltered(@PathVariable("nq") Optional<String> name, @PathVariable("uq") Optional<Integer> uid,
                                        @PathVariable("gq") Optional<Integer> gid, @PathVariable("cq") Optional<String> comment,
                                        @PathVariable("hq") Optional<String> home, @PathVariable("sq") Optional<String> shell){

        List<Users> names, uids, gids, comments, homes, shells;
        List<List> temp = new ArrayList<List>();

        if(name.isPresent()){
            names = this.usersRepository.findAllByname(name.toString());
            temp.add(names);
        }

        if(uid.isPresent()){
            uids = this.usersRepository.findAllByuid(Integer.valueOf(uid.toString()));
            temp.add(uids);
        }

        if(gid.isPresent()){
            gids = this.usersRepository.findAllBygid(Integer.valueOf(gid.toString()));
            temp.add(gids);
        }

        if(comment.isPresent()){
            comments = this.usersRepository.findAllBycomment(comment.toString());
            temp.add(comments);
        }

        if(home.isPresent()){
            homes = this.usersRepository.findAllByhome(home.toString());
            temp.add(homes);
        }

        if(shell.isPresent()){
            shells = this.usersRepository.findAllByshell(shell.toString());
            temp.add(shells);
        }

        //find common users
        if(temp.isEmpty()){
            List<Users> query = temp.get(0);
            for(int i = 1; i < temp.size(); i++){
                query.retainAll(temp.get(i));
            }
            return query;
        }

        //return all users by default
        return this.usersRepository.findAll();
    }

    @GetMapping("/users/{uid}/groups")
    public List<Groups> getUsersGroups(@PathVariable("uid") int uid){
        Users user = this.usersRepository.findByuid(uid);
        return this.groupsRepository.findAllBymembers(user.getName());
    }

    @GetMapping("/groups")
    public List<Groups> getGroups(){ return this.groupsRepository.findAll();}

    @GetMapping("/groups/query?groups/query?name={nq}&gid={gq}&members={mq}")
    public List<Groups> getUsersFiltered(@PathVariable("nq") Optional<String> name, @PathVariable("gq") Optional<Integer> gid,
                                         @PathVariable("mq") Optional<String> member){

        List<Groups> names, gids, members; //lists which may be empty
        List<List> temp = new ArrayList<List>(); //stores non-empty lists of groups for finding common groups

        //Check if any of the optional parameters are filled out
        if(name.isPresent()){
            names = this.groupsRepository.findAllByname(name.toString());
            //return if this is the only parameter
            if(!gid.isPresent() && !member.isPresent()){
                if(!names.isEmpty()){
                    return names;
                }
            }
            temp.add(names);
        }

        if(gid.isPresent()){
            gids = this.groupsRepository.findAllBygid(Integer.valueOf(gid.toString()));
            //return if this the only parameter
            if(!name.isPresent() && !member.isPresent()){
                if(!gids.isEmpty()){
                    return gids;
                }
            }
            temp.add(gids);
        }

        if(member.isPresent()){
            members = this.groupsRepository.findAllBymembers(member.toString());
            if(!name.isPresent() && !gid.isPresent()){
                if(!members.isEmpty()){
                    return members;
                }
            }
            temp.add(members);
        }

        //find common groups
        if(temp.isEmpty()){
            List<Groups> query = temp.get(0);
            for(int i = 1; i < temp.size(); i++){
                query.retainAll(temp.get(i));
            }
            return query;
        }

        //return all groups by default
        return this.groupsRepository.findAll();
    }

    @GetMapping("/groups/{gid}")
    public Groups getByGid(@PathVariable("gid") int gid){
        Groups groups = this.groupsRepository.findBygid(gid);
        if(groups == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        return groups;
    }
}
