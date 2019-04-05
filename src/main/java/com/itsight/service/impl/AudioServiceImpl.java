package com.itsight.service.impl;

import com.itsight.domain.Audio;
import com.itsight.domain.TipoAudio;
import com.itsight.generic.BaseServiceImpl;
import com.itsight.repository.AudioRepository;
import com.itsight.service.AudioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AudioServiceImpl extends BaseServiceImpl<AudioRepository> implements AudioService {

    public AudioServiceImpl(AudioRepository repository) {
        super(repository);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<Audio> findAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public Audio save(Audio audio) {
        // TODO Auto-generated method stub
        return repository.save(audio);
    }

    @Override
    public Audio update(Audio audio) {
        // TODO Auto-generated method stub
        return repository.saveAndFlush(audio);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        repository.deleteById(id);
    }

    @Override
    public Audio findOne(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Audio> findByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return repository.findAllByFlagActivo(flagActivo);
    }

    @Override
    public List<Audio> findByNombre(String nombres) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContaining(nombres);
    }

    @Override
    public Audio findOneWithFT(Integer id) {
        // TODO Auto-generated method stub
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Integer> findIdsByFlagActivo(boolean flagActivo) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Audio> findByNombreContainingIgnoreCase(String nombre) {
        // TODO Auto-generated method stub
        return repository.findAllByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Audio> findByDescripcionContainingIgnoreCase(String descripcion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Audio> findByFlagEliminado(boolean flagEliminado) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Audio> findByIdsIn(List<Integer> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Audio> listarPorFiltro(String comodin, String estado, String tipoAudio) {
        // TODO Auto-generated method stub
        List<Audio> lstAudio;
        if (comodin.equals("0") && estado.equals("-1") && tipoAudio.equals("0")) {
            lstAudio = repository.findAll();
        } else {
            if (comodin.equals("0") && tipoAudio.equals("0")) {
                lstAudio = repository.findAllByFlagActivo(Boolean.valueOf(estado));
            } else {
                comodin = comodin.equals("0") ? "" : comodin;

                lstAudio = repository.findAllByNombreContainingIgnoreCase(comodin);

                if (!estado.equals("-1")) {
                    lstAudio = lstAudio.stream()
                            .filter(x -> Boolean.valueOf(estado).equals(x.isFlagActivo()))
                            .collect(Collectors.toList());
                }

                if (!tipoAudio.equals("0")) {
                    lstAudio = lstAudio.stream()
                            .filter(x -> tipoAudio.equals(String.valueOf(x.getTipoAudio().getId())))
                            .collect(Collectors.toList());
                }
            }
        }
        return lstAudio;
    }

    @Override
    public String registrar(Audio entity, String wildcard) {
        // TODO Auto-generated method stub
        return String.valueOf(repository.save(entity).getId());
    }

    @Override
    public String actualizar(Audio entity, String wildcard) {
        // TODO Auto-generated method stub
        return String.valueOf(repository.saveAndFlush(entity).getId());
    }

    @Override
    public void actualizarFlagActivoById(Integer id, boolean flagActivo) {
        // TODO Auto-generated method stub
        repository.updateFlagActivoById(id, flagActivo);
    }

    @Override
    public List<Audio> listarPorFiltroSecundario(String comodin, String tipo) {
        comodin = comodin.equals("") ? null : comodin;
        tipo = tipo.equals("") ? null : tipo;
        List<Audio> lstAudio;
        if (comodin == null && tipo == null)
            lstAudio = repository.findAllByFlagActivoTrueOrderByIdDesc();
        else if (comodin == null)
            lstAudio = repository.findAllByTipoAudioIdAndFlagActivoTrueOrderByIdDesc(Integer.parseInt(tipo));
        else if (tipo == null)
            lstAudio = repository.findAllByNombreContainingIgnoreCaseAndFlagActivoTrueOrderByIdDesc(comodin);
        else
            lstAudio = repository.findAllByTipoAudioIdAndNombreContainingIgnoreCaseAndFlagActivoTrueOrderByIdDesc(Integer.parseInt(tipo), comodin);
        return lstAudio;
    }
}
