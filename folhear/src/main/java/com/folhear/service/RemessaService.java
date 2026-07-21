package com.folhear.service;

import com.folhear.entity.Remessa;
import com.folhear.entity.Transacao;
import com.folhear.exception.ResourceNotFoundException;
import com.folhear.repository.RemessaRepository;
import com.folhear.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemessaService {

    @Autowired
    private RemessaRepository remessaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public Remessa create(Remessa remessa) {
        resolverReferencias(remessa);
        return remessaRepository.save(remessa);
    }

    public Remessa findById(Long id) {
        return remessaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Remessa", id));
    }

    public List<Remessa> findAll() {
        return remessaRepository.findAll();
    }

    public Remessa update(Long id, Remessa dadosAtualizados) {
        findById(id);
        dadosAtualizados.setId(id);
        resolverReferencias(dadosAtualizados);
        return remessaRepository.save(dadosAtualizados);
    }

    public void delete(Long id) {
        findById(id);
        remessaRepository.deleteById(id);
    }

    /**
     * Garante que "transacao" enviada no JSON (apenas com id) vire uma
     * referência gerenciada de verdade, evitando TransientPropertyValueException.
     */
    private void resolverReferencias(Remessa remessa) {
        if (remessa.getTransacao() != null && remessa.getTransacao().getId() != null) {
            Transacao transacaoRef = transacaoRepository.getReferenceById(remessa.getTransacao().getId());
            remessa.setTransacao(transacaoRef);
        }
    }
}
