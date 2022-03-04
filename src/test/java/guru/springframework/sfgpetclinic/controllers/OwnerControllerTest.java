package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    public static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    OwnerController controller;

    @Test
    void shouldProcessCreationFormWithErrors() {
        // given
        given(bindingResult.hasErrors()).willReturn(true);

        // when
        String actual = controller.processCreationForm(new Owner(1L, "John", "Buck"), bindingResult);

        // then
        then(bindingResult).should().hasErrors();
        assertThat(actual).isEqualTo(VIEWS_OWNER_CREATE_OR_UPDATE_FORM);
    }

    @Test
    void testProcessCreationFromWithNoErrors() {
        // given
        Owner owner = new Owner(5L, "John", "Buck");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);

        // when
        String actual = controller.processCreationForm(owner, bindingResult);

        // then
        then(ownerService).should().save(any());
        assertThat(actual).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }
}