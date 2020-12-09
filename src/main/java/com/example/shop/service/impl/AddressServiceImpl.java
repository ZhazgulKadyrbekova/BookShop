package com.example.shop.service.impl;

import com.example.shop.entity.AddressEntity;
import com.example.shop.entity.HistoryEntity;
import com.example.shop.entity.UserEntity;
import com.example.shop.exception.AddressNotFoundException;
import com.example.shop.repository.AddressRepository;
import com.example.shop.repository.HistoryRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AddressEntity> getAll() {
        return addressRepository.findAllByDeleted(false);
    }

    @Override
    public AddressEntity createAddress(AddressEntity address, String email) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(address.getCity());
        addressEntity.setDistrict(address.getDistrict());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setHouse(address.getHouse());
        addressEntity.setApartment(address.getApartment());
        addressRepository.save(addressEntity);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("ADDRESS", "CREATE ", user);
        historyRepository.save(history);

        return addressEntity;
    }

    @Override
    public AddressEntity findById(Integer id) {
        AddressEntity address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address id " + id + " not found!"));
        if (address.isDeleted()) {
            throw new AddressNotFoundException("Address id " + id + " not found!");
        }
        return address;
    }

    @Override
    public AddressEntity update(AddressEntity address, String email) {
        AddressEntity addressEntity = addressRepository.findById(address.getID())
                .orElseThrow(() -> new AddressNotFoundException("Address id " + address.getID() + " not found!"));
        if (addressEntity.isDeleted()) {
            throw new AddressNotFoundException("Address id " + address.getID() + " not found!");
        }
        addressEntity.setCity(address.getCity());
        addressEntity.setDistrict(address.getDistrict());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setHouse(address.getHouse());
        addressEntity.setApartment(address.getApartment());
        addressEntity.setDeleted(address.isDeleted());
        addressRepository.save(addressEntity);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("ADDRESS", "UPDATE", user);
        historyRepository.save(history);

        return addressEntity;
    }

    @Override
    public String deleteById(Integer id, String email) {
        AddressEntity address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address id " + id + " not found!"));
        if (address.isDeleted()) {
            throw new AddressNotFoundException("Address id " + address.getID() + " not found!");
        }
        address.setDeleted(true);
        addressRepository.save(address);

        UserEntity user = userRepository.findByEmail(email);
        HistoryEntity history = new
                HistoryEntity("ADDRESS", "DELETE", user);
        historyRepository.save(history);

        return "Address number " + id + " has been deleted!";

    }
}
