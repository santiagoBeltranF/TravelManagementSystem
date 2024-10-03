package com.parcial.hosting_service.servicies.impl;

import com.parcial.hosting_service.clients.DestinyClient;
import com.parcial.hosting_service.config.Constantes;
import com.parcial.hosting_service.dto.DestinyDTO;
import com.parcial.hosting_service.dto.FeatureDTO;
import com.parcial.hosting_service.dto.HostDTO;
import com.parcial.hosting_service.dto.PictureDTO;
import com.parcial.hosting_service.models.Feature;
import com.parcial.hosting_service.models.Host;
import com.parcial.hosting_service.models.Picture;
import com.parcial.hosting_service.reposotories.HostRepository;
import com.parcial.hosting_service.servicies.FeatureService;
import com.parcial.hosting_service.servicies.HostService;
import com.parcial.hosting_service.servicies.PictureService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HostServiceImpl implements HostService {

    @Autowired
    private final FeatureService featureService;
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private final HostRepository hostRepository;

    @Autowired
    private final PictureService pictureService;

    @Autowired
    private final DestinyClient destinyClient;

    @Override
    public Host save(HostDTO hostDTO, FeatureDTO featureDTO, PictureDTO pictureDTO) {
        Optional<Host> guardado = hostRepository.findByName(hostDTO.getName());

        if (guardado.isPresent()) {
            throw new RuntimeException("El alojamiento con el nombre " + hostDTO.getName() + " ya existe");
        }

        // Validar el destino usando destinyClient
        DestinyDTO destinyDTO = destinyClient.getDestinyByName(hostDTO.getDestinyName());
        if (destinyDTO == null) {
            throw new RuntimeException("El destino con el nombre " + hostDTO.getDestinyName() + " no existe");
        }

        Feature feature = featureService.save(featureDTO);
        Picture picture = pictureService.save(pictureDTO);

        Host nuevoHost = factory(hostDTO, feature, picture);

        return hostRepository.save(nuevoHost);
    }

    public Host findByName(String name) {
        return hostRepository.findByName(name).orElse(null);
    }

    @Override
    public List<Host> findByDestinyName(String destinyName) {
        return hostRepository.findByDestinyName(destinyName);
    }

    @Override
    public Host update(String name, HostDTO hostDTO, FeatureDTO featureDTO, PictureDTO pictureDTO) {
        Host existingHost = findByName(name);

        if (existingHost == null) {
            throw new RuntimeException("El alojamiento con el nombre " + name + " no existe");
        }

        // Validar el destino usando destinyClient
        DestinyDTO destinyDTO = destinyClient.getDestinyByName(hostDTO.getDestinyName());
        if (destinyDTO == null) {
            throw new RuntimeException("El destino con el nombre " + hostDTO.getDestinyName() + " no existe");
        }

        // Actualizar campos del alojamiento existente
        existingHost.setName(hostDTO.getName());
        existingHost.setRating(hostDTO.getRating());
        existingHost.setPrice(hostDTO.getPrice());
        existingHost.setMaximumCapacity(hostDTO.getMaximumCapacity());
        existingHost.setLatitude(hostDTO.getLatitude());
        existingHost.setLongitude(hostDTO.getLongitude());
        existingHost.setDestinyName(hostDTO.getDestinyName());

        // Actualizar o guardar la feature
        Feature feature = featureService.save(featureDTO);
        existingHost.setFeature(feature);

        // Actualizar o guardar la imagen
        Picture picture = pictureService.save(pictureDTO);
        existingHost.setPicture(picture);

        return hostRepository.save(existingHost);
    }

    @Override
    public Host factory(HostDTO hostDTO, Feature feature, Picture picture) {
        return Host.builder()
                .name(hostDTO.getName())
                .rating(hostDTO.getRating())
                .price(hostDTO.getPrice())
                .maximumCapacity(hostDTO.getMaximumCapacity())
                .latitude(hostDTO.getLatitude())
                .longitude(hostDTO.getLongitude())
                .picture(picture)
                .feature(feature)
                .destinyName(hostDTO.getDestinyName())
                .build();
    }

    @Override
    public void deleteByName(String name) {
        Host existingHost = findByName(name);
        if (existingHost == null) {
            throw new RuntimeException("El alojamiento con el nombre " + name + " no existe");
        }
        hostRepository.delete(existingHost);
    }

    private void validarNombreDestino(String nombreDestino) {
        Object respuesta = rabbitTemplate.convertSendAndReceive(Constantes.EXCHANGE, Constantes.ROUTING_KEY,
                nombreDestino);
        if (Objects.isNull(respuesta)) {
            throw new RuntimeException("Hubo un error recuperando la información del destino");
        }
        boolean existe = (Boolean) respuesta;
        if (!existe) {
            throw new RuntimeException("El destino con el nombre: " + nombreDestino + " no existe");
        }
    }

}
