package br.com.fiap.epictaskapi.config;

import java.util.Arrays;
import java.util.List;

import br.com.fiap.epictaskapi.model.Role;
import br.com.fiap.epictaskapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.epictaskapi.model.Task;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.TaskRepository;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Configuration
public class DatabaseSeed implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        
        taskRepository.saveAll(List.of(
            new Task("Modelar o BD", "Modelar as tabelas do banco de dados"),
            new Task("Protipar telas", "Modelar as tabelas do banco de dados"),
            new Task("Bug", "Modelar as tabelas do banco de dados"),
            new Task("Deploy", "Modelar as tabelas do banco de dados"),
            new Task("Login", "Modelar as tabelas do banco de dados"),
            new Task("Outro Bug", "Modelar as tabelas do banco de dados"),
            new Task("Logout", "Modelar as tabelas do banco de dados"),
            new Task("Cadastro de cliente", "Modelar as tabelas do banco de dados"),
            new Task("Consulta de cliente", "Modelar as tabelas do banco de dados")
        ));

        Role adm = new Role("Administrador");
        Role operador = new Role("operador");
        roleRepository.saveAll(Arrays.asList(adm, operador));

        User user = new User("João", "joao@fiap.com.br", passwordEncoder.encode("123"));
        User user1 = new User("Giovana", "giovana@fiap.com.br", passwordEncoder.encode("456"));
        User user2 = new User("Emily", "emily@fiap.com.br", passwordEncoder.encode("789"));
        User user3 = new User("Diego", "diego@fiap.com.br", passwordEncoder.encode("012"));
        User user4 = new User("Gabriel", "gabriel@fiap.com.br", passwordEncoder.encode("345"));
        user.addRole(adm);
        user1.addRole(adm);
        user2.addRole(adm);
        user3.addRole(adm);
        user4.addRole(adm);
        user.addRole(operador);
        user1.addRole(operador);
        user2.addRole(operador);
        user3.addRole(operador);
        user4.addRole(operador);
        userRepository.saveAll(Arrays.asList(user, user1, user2, user3, user4));
    }
    
}
