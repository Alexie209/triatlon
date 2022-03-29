package com.example.triatlon.service;

import com.example.triatlon.domain.Arbitru;
import com.example.triatlon.domain.Jucator;
import com.example.triatlon.domain.ParticipantProba;
import com.example.triatlon.domain.Proba;
import com.example.triatlon.repository.Repository;
import org.apache.logging.log4j.message.Message;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Service {
    private final Repository<Long, Arbitru> arbitruRepository;
    private final Repository<Long, Jucator> jucatorRepository;
    private final Repository<Long, Proba> probaRepository;
    private final Repository<Long, ParticipantProba> participantProbaRepository;


    public Service(Repository<Long, Arbitru> arbitruRepository, Repository<Long, Jucator> jucatorRepository, Repository<Long, Proba> probaRepository, Repository<Long, ParticipantProba> participantProbaRepository) {
        this.arbitruRepository = arbitruRepository;
        this.jucatorRepository = jucatorRepository;
        this.probaRepository = probaRepository;
        this.participantProbaRepository = participantProbaRepository;
    }

    public void saveArbitru(String firstName, String lastName, String username, String password) {
        Arbitru arbitru = new Arbitru(firstName, lastName, username, password);
        this.arbitruRepository.save(arbitru);
    }

//    public void updateUser(Long id, String firstName, String lastName, String username, String password) {
//        for (User user : this.userRepository.findAll()) {
//            if (user.getId().equals(id)) {
//                User user1 = new User(firstName, lastName, username, password);
//                user1.setId(user.getId());
//                this.userRepository.update(user1);
//
//            }
//        }
//    }

//    public void saveFriendship(Long id1, Long id2, LocalDate friendRequestDate, String usernameId1, String usernameId2) {
//        boolean ok = true;
//        if (id1 == null || id2 == null) {
//            throw new IllegalArgumentException("Id's must be not null");
//        }
//        User user1 = userRepository.findOne(id1);
//        User user2 = userRepository.findOne(id2);
//        user1.setId(id1);
//        user2.setId(id2);
//        for (User user : user1.getFriends()) {
//            if (Objects.equals(user.getId(), id2)) {
//                ok = false;
//                break;
//            }
//        }
//        for (User user : user2.getFriends()) {
//            if (Objects.equals(user.getId(), id1)) {
//                ok = false;
//                break;
//            }
//        }
//        if (ok) {
//            user1.makeFriend(user2);
//            user2.makeFriend(user1);
//            this.friendshipRepository.save(new Friendship(id1, id2, LocalDate.now(), usernameId1, usernameId2));
//        }
//    }

    public void deleteArbitru(Long id) {
        this.arbitruRepository.delete(id);
    }

    public void deleteJucator(Long id) {
        this.jucatorRepository.delete(id);
    }

    public void deleteProba(Long id) {
        this.probaRepository.delete(id);
    }

    public void deleteParticipantProba(Long id) {
        this.participantProbaRepository.delete(id);
    }

    public Arbitru findOne(Long x) {
        return this.arbitruRepository.findOne(x);
    }

    public List<Arbitru> printAll() {
        return this.arbitruRepository.findAll();
    }
    public List<Jucator> printAllJucatori() {
        return this.jucatorRepository.findAll();
    }
    public void sortedJucatori(ArrayList<Jucator> list) {
        list.sort(((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName())));
    }
    public void sortedParticipantProba(ArrayList<ParticipantProba> list) {
        list.sort(((o1, o2) -> o2.getPuncteStranse().compareTo(o1.getPuncteStranse())));
    }
    public List<Proba> printAllProba() {
        return this.probaRepository.findAll();
    }
    public void addPuncteProba(Long idJucator, Long idProba, int puncteStranse) {
        ParticipantProba newParticipantProba =new ParticipantProba(idJucator, idProba,puncteStranse);
        this.participantProbaRepository.save(newParticipantProba);
        Jucator jucator = this.jucatorRepository.findOne(idJucator);
        int totalPuncte = jucator.getTotalPuncte() + puncteStranse;
        updateJucator(idJucator, jucator.getFirstName(), jucator.getLastName(), totalPuncte);

    }

    public Integer addTotalPuncte(int puncteStranse, int totalPuncte) {
        totalPuncte += puncteStranse;
        return totalPuncte;
    }
    public void updateJucator(Long id, String firstName, String lastName, int totalPuncte) {
        for(Jucator jucator : this.jucatorRepository.findAll()) {
            if(jucator.getId().equals(id)) {
                Jucator jucator1 = new Jucator(firstName, lastName, totalPuncte);
                jucator1.setId(jucator.getId());
                this.jucatorRepository.update(jucator1);
            }
        }
    }
//    public List<Jucator> printAllSortedJucatori() {
//        return SortedJucatori();
//    }

//    public List<User> getFriends(User user) {
//        return this.userRepository.getFriends(user);
//    }
//
//    public List<Friendship> printAllFriendships() {
//        return this.friendshipRepository.findAll();
//    }
//
//    public List<Friendship> getFriendshipRelations(Long aLong) {
//        List<Friendship> getAll = friendshipRepository.findAll();
//        Predicate<Friendship> filterCriteria = x -> Objects.equals(x.getId1(), aLong) || Objects.equals(x.getId2(), aLong);
//        return getAll.stream()
//                .filter(filterCriteria)
//                .toList();
//    }
//
//    public List<Friendship> getFriendshipRelationsByMonth(Long aLong, int month) {
//        List<Friendship> getAll = friendshipRepository.findAll();
//        Predicate<Friendship> filterById = x -> Objects.equals(x.getId1(), aLong) || Objects.equals(x.getId2(), aLong);
//        Predicate<Friendship> filterByMonth = x -> x.getFriendshipDate().getMonthValue() == month;
//        Predicate<Friendship> filterCriteria = filterById.and(filterByMonth);
//        return getAll.stream()
//                .filter(filterCriteria)
//                .toList();
//    }
//
//    public void sendMessage(Long from, Long to, String text, String toUsername, String fromUsername) {
//        if (from == null || to == null) {
//            throw new IllegalArgumentException("Id's must be not null");
//        }
//        List<Message> conversation = showConversation(from, to);
//        Message newMessage = new Message(from, to, text, LocalDate.now(), 0L, toUsername, fromUsername);
//        if (conversation.size() == 0 || conversation.get(conversation.size() - 1).getFrom().equals(from)) {
//            newMessage.setReply(0L);
//        } else {
//            newMessage.setReply(to);
//        }
//        this.messageRepository.save(newMessage);
//    }
//
//    public void deleteMessage(Long id) {
//        this.messageRepository.delete(id);
//    }
//
//    public List<Message> printAllMessages() {
//        return messageRepository.findAll();
//    }
//
//    public List<Message> showConversation(Long id1, Long id2) {
//        List<Message> conversation = new ArrayList<>();
//        for (Message message : printAllMessages()) {
//            if ((message.getFrom().equals(id1) && message.getTo().equals(id2)) || (message.getFrom().equals(id2) && message.getTo().equals(id1))) {
//                conversation.add(message);
//            }
//        }
//        List<Message> sortedMessages = conversation.stream()
//                .sorted(Comparator.comparing(Message::getDate))
//                .collect(Collectors.toList());
//
//        return sortedMessages;
//    }
//
//
//    public void sendFriendRequest(Long id1, Long id2, String usernameId1, String usernameId2) {
//        boolean ok = true;
//        if (id1 == null || id2 == null) {
//            throw new IllegalArgumentException("Id's must be not null");
//        }
//        List<Friendship> getAll = printAllFriendships();
//        for (Friendship friendship : getAll) {
//            if ((friendship.getId1().equals(id1) && friendship.getId2().equals(id2)) || friendship.getId1().equals(id2) && friendship.getId2().equals(id1)) {
//                ok = false;
//                break;
//            }
//        }
//        if (ok) {
//            String status = String.valueOf(Status.PENDING);
//            LocalDate friendRequestDate = LocalDate.now();
//            this.friendRequestRepository.save(new FriendRequest(id1, id2, status, friendRequestDate, usernameId1, usernameId2));
//        } else {
//            throw new IllegalArgumentException("The friend request can't be sent!Friendship already exists");
//        }
//    }
//
//    public List<FriendRequest> printAllFriendRequests() {
//        return this.friendRequestRepository.findAll();
//    }
//
//    public void deleteFriendRequests(Long id) {
//        this.friendRequestRepository.delete(id);
//    }
//
//    public void updateFriendRequest(Long id, Status status) {
//        FriendRequest friendRequest = this.friendRequestRepository.findOne(id);
//        if (status == Status.REJECTED) {
//            this.friendRequestRepository.delete(id);
//        } else if (status == Status.APPROVED) {
//            deleteFriendRequests(id);
//            this.saveFriendship(friendRequest.getId1(), friendRequest.getId2(), friendRequest.getFriendRequestDate(), friendRequest.getUsernameId1(), friendRequest.getUsernameId2());
//        }
//    }

}
